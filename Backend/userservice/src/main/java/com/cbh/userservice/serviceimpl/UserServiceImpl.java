package com.cbh.userservice.serviceimpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbh.userservice.models.Address;
import com.cbh.userservice.models.User;
import com.cbh.userservice.repository.UserRepository;
import com.cbh.userservice.requestdto.AddUser;
import com.cbh.userservice.requestdto.DoKyc;
import com.cbh.userservice.service.UserService;
import com.cbh.userservice.models.Kyc;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User addUser(AddUser addUser) {
		User user=mapper.map(addUser, User.class);
		Address address=mapper.map(addUser, Address.class);
		user.setKycStatus("Not Initialzed");
		user.setAddress(address);
		userRepository.save(user);
		return user;
	}

	@Override
	public User Kyc(DoKyc doKyc) {
		Optional<User> user=userRepository.findByEmail(doKyc.getEmail());
		if(user.isEmpty()) {
			
		}
		Kyc kyc=mapper.map(doKyc,Kyc.class);
		user.get().setKyc(kyc);
		user.get().setKycStatus("Applied");
		userRepository.save(user.get());
		return user.get();
	}

}
