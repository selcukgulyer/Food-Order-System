package com.example.demo.controller.request;

import com.example.demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {

    @NotBlank(message = "Iban field cannot be empty")
    private String iban;
    private User user;
    @NotBlank(message = "Card number field cannot be empty")
    private String cardNumber;
    @NotBlank(message = "Cvc field cannot be empty")
    @Size(min = 3,max = 3)
    private String cvc;
    @NotBlank(message = "Last usage date field cannot be empty")
    private String lastUsageDate;
}
