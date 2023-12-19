package com.cbh.userservice.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cbh.userservice.models.Address;
import com.cbh.userservice.models.User;
import com.cbh.userservice.repository.UserRepository;
import com.cbh.userservice.requestdto.AddUser;
import com.cbh.userservice.requestdto.DoKyc;
import com.cbh.userservice.service.UserService;
import com.cbh.userservice.util.Constant;
import com.cbh.userservice.models.Kyc;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	
	private final ModelMapper mapper;
	
	private final UserRepository userRepository;
	
	public User addUser(AddUser addUser) {
		fetchUserByEmail(addUser.getEmail());
		User user=mapper.map(addUser, User.class);
		Address address=mapper.map(addUser, Address.class);
		user.setKycStatus(Constant.KYCSTATUS_NOTINITIALISED);
		user.setAddress(address);
		userRepository.save(user);
		return user;
	}

	public User kyc(DoKyc doKyc) {
		User user=fetchUserByEmail(doKyc.getEmail());
		Kyc kyc=mapper.map(doKyc,Kyc.class);
		user.setKyc(kyc);
		user.setKycStatus(Constant.KYCSTATUS_APPLIED);
		userRepository.save(user);
		return user;
	}
	
	public User fetchUserByEmail(String email) {
	    return userRepository.findByEmail(email).orElseThrow();
	}

	public User fetchUserById(String userId) {
	    return userRepository.findByUserId(userId).orElseThrow();
	}

}
