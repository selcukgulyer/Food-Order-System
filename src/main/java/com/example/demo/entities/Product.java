package com.example.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@EqualsAndHashCode
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

    public Product(String productName, int stock, ProductStatus productStatus, double unitPrice, List<Order> order) {
        this.productName = productName;
        this.stock = stock;
        this.productStatus = productStatus;
        this.unitPrice = unitPrice;
        this.order = order;
    }


}
