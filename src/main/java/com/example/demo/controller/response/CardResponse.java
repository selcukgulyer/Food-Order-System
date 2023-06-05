package com.example.demo.controller.response;

import com.example.demo.entities.CardDetails;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
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
        if (CollectionUtils.isEmpty(cards))
            return new ArrayList<>();

        return cards.stream()
                .map(CardResponse::from)
                .collect(Collectors.toList());
    }


}
