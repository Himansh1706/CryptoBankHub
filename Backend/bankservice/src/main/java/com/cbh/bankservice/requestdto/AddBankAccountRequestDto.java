package com.cbh.bankservice.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddBankAccountRequestDto {
	
	private String userId;
	private String bankName;
	private String bankAccountType;
}
