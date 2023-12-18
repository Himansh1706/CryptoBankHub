package com.cbh.userservice.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUser {
	private String name;
    private String email;
    private String mobileNumber;
    private String dob;
    private String password;
    private String permanentAddress;
	private String city;
	private String pinCode;
}
