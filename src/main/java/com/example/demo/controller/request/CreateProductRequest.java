package com.example.demo.controller.request;

import com.example.demo.entities.Order;
import com.example.demo.entities.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private int id;
    private String productName;
    private int stock;
    private ProductStatus productStatus;
    private Double unitPrice;
    private List<Order> order;
}
