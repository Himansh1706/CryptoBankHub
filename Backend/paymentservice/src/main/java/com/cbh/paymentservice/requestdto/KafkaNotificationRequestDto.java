package com.cbh.paymentservice.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaNotificationRequestDto {

	private Object message;
	private String topic;
	private String key;
	private String email;
	private String subject;

}
