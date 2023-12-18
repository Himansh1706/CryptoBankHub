package com.cbh.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbh.userservice.models.User;
import com.cbh.userservice.requestdto.AddUser;
import com.cbh.userservice.requestdto.DoKyc;
import com.cbh.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<User> add(@RequestBody AddUser addUser){
		User user=userService.addUser(addUser);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/kyc")
	public ResponseEntity<User> kyc(@RequestBody DoKyc doKyc){
		User user=userService.Kyc(doKyc);
		return ResponseEntity.ok(user);
	}
	
}
