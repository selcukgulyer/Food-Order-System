package com.example.demo.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card_detail")
@ToString
public class CardDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String iban;
    private String cardNumber;
    private String cvc;
    private String lastUsageDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CardDetails(String iban, String cardNumber, String cvc, String lastUsageDate, User user) {
        this.iban = iban;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.lastUsageDate = lastUsageDate;
        this.user = user;
    }
}
