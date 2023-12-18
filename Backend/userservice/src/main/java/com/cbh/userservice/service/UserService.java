package com.cbh.userservice.service;

import com.cbh.userservice.models.User;
import com.cbh.userservice.requestdto.AddUser;
import com.cbh.userservice.requestdto.DoKyc;

public interface UserService {

	User addUser(AddUser addUser);
	
	User Kyc(DoKyc doKyc);
}
