package com.example.demo.controller.request;

import com.example.demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {
    private int id;
    private String iban;
    private User user;
    private String cardNumber;
    private String cvc;
    private String lastUsageDate;
}
