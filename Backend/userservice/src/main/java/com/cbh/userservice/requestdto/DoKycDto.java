package com.cbh.userservice.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoKycDto {
	
	private String email;
	private String aadharCard;
	private String panCard;

}
