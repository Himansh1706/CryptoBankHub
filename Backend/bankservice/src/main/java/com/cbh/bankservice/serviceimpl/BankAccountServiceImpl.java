package com.cbh.bankservice.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cbh.bankservice.config.KafkaPublisher;
import com.cbh.bankservice.config.WebclientConfig;
import com.cbh.bankservice.model.BankAccount;
import com.cbh.bankservice.repository.BankAccountRepository;
import com.cbh.bankservice.requestdto.AddBankAccountRequestDto;
import com.cbh.bankservice.requestdto.CheckBalanceRequestDto;
import com.cbh.bankservice.requestdto.KafkaNotificationRequestDto;
import com.cbh.bankservice.requestdto.UpdateBalanceRequestDto;
import com.cbh.bankservice.responsedto.KycStatusResponseDto;
import com.cbh.bankservice.service.BankAccountService;
import com.cbh.exceptionservice.exception.NotFoundException;
import com.cbh.exceptionservice.exception.UserBlockedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

	private final ModelMapper mapper;
	private final BankAccountRepository bankAccountRepository;
	private final WebclientConfig webclientConfig;
	private final KafkaPublisher kafkaPublisher;

	public BankAccount add(AddBankAccountRequestDto addBankAccountRequestDto) {
		BankAccount bankAccount = mapper.map(addBankAccountRequestDto, BankAccount.class);
		bankAccount.setBalance(0.0);
		KycStatusResponseDto kycStatusResponseDto= webclientConfig.get(
				String.format("http://localhost:1011/user/%s", addBankAccountRequestDto.getUserId()), KycStatusResponseDto.class);
		if (!"Applied".equals(kycStatusResponseDto.getKycStatus())) {
			throw new UserBlockedException("User has not done kyc userid - ", addBankAccountRequestDto.getUserId());
		}
		
		BankAccount addedBankAccount=bankAccountRepository.save(bankAccount);
		KafkaNotificationRequestDto kafkaNotificationRequestDto = createNotificationRequest(kycStatusResponseDto.getEmail(),
				"addedBankAccount", "userNotifications", "Update from CryptoBankHub",
				"Congratulations Bank Account added successfully. Yor account number is  "+addedBankAccount.getBankAccountNumber());

		kafkaPublisher.sendNotificationToUser(kafkaNotificationRequestDto,
				(topic, response) -> {});
		return addedBankAccount;
	}

	public double checkBalance(CheckBalanceRequestDto checkBalanceRequestDto) {
		return bankAccountRepository.findById(checkBalanceRequestDto.getBankAccountNumber())
				.filter(bankAccount -> bankAccount.getUserId().equals(checkBalanceRequestDto.getUserId()))
				.map(BankAccount::getBalance)
				.orElseThrow(() -> new NotFoundException("Bank account not found or user mismatch", ""));

	}

	public double updateBalance(UpdateBalanceRequestDto updateBalanceRequestDto) {
		return bankAccountRepository.findById(updateBalanceRequestDto.getBankAccountNumber()).map(bankAccount -> {
			double newBalance = 0.0;
			if ("Withdraw".equalsIgnoreCase(updateBalanceRequestDto.getTransactionType())) {
				newBalance = bankAccount.getBalance() - updateBalanceRequestDto.getAmount();
				if (newBalance < 0) {
					throw new RuntimeException("Insufficient balance");
				}
			} else {
				newBalance = bankAccount.getBalance() + updateBalanceRequestDto.getAmount();
			}
			bankAccount.setBalance(newBalance);
			bankAccountRepository.save(bankAccount);
			return newBalance;
		}).orElseThrow(() -> new NotFoundException("", ""));
	}

	public List<BankAccount> getBankAccounts(String userId) {
		return bankAccountRepository.findByUserId(userId);
	}
	
	private KafkaNotificationRequestDto createNotificationRequest(String email, String key, String topic,
			String subject, String message) {
		return KafkaNotificationRequestDto.builder().email(email).key(key).topic(topic).subject(subject)
				.message(message).build();
	}

}
