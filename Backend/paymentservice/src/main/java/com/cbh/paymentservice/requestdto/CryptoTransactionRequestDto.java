package com.cbh.paymentservice.requestdto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoTransactionRequestDto {
	
	private String userId;
	private String cryptoId;
	private BigDecimal price;
	private Integer quantity;
	private String bankName;
	private Long accountNumber;

}
