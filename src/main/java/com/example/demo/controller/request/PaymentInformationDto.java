package com.example.demo.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInformationDto {
    private String cardNumber;
    private String cvc;
    private String lastUsageDate;

}
