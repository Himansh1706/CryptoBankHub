package com.cbh.userservice.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class representing the information needed to add a new user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {
	private String name;
    private String email;
    private String mobileNumber;
    private String dob;
    private String password;
    private String permanentAddress;
	private String city;
	private String pinCode;
}
