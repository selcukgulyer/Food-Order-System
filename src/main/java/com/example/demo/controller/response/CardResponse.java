package com.example.demo.controller.response;

import com.example.demo.entities.CardDetails;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CardResponse {
    private String iban;
    private String cardNumber;
    private String cvc;
    private String lastUsageDate;


    public static CardResponse from(CardDetails card) {
        return new CardResponse(
                card.getIban(),
                card.getCardNumber(),
                card.getCvc(),
                card.getLastUsageDate()
        );
    }

    public static List<CardResponse> convert(List<CardDetails> cards) {
        return cards.stream()
                .map(card -> CardResponse.from(card))
                .collect(Collectors.toList());
    }


}
