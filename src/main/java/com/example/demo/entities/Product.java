package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "stock")
    private int stock;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Column(name = "unit_price")
    private double unitPrice;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Order> order = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", stock=" + stock +
                ", productStatus=" + productStatus +
                ", unitPrice=" + unitPrice +
                ", order=" + order +
                '}';
    }

}
