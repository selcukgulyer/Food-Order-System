package com.example.demo.entities;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_table")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.INITIAL;

    @Column(name = "piece")
    private int piece;

    @Column(name = "total")
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Order(OrderStatus status, int piece, double total, User user, Product product) {
        this.status = status;
        this.piece = piece;
        this.total = total;
        this.user = user;
        this.product = product;
    }
}
