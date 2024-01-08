package com.cbh.bankservice.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycStatusResponseDto {

    private String email;
    private String kycStatus;
}
