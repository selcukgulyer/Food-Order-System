package com.example.demo.controller.request;

import com.example.demo.entities.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    private String productName;
    private int stock;
    private ProductStatus productStatus;
    private Double unitPrice;
}
