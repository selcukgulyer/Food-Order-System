package com.example.demo.controller.response;

import com.example.demo.entities.Product;
import com.example.demo.entities.ProductStatus;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponse {

    private String productName;
    private int stock;
    private ProductStatus productStatus;
    private double unitPrice;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProductName(),
                product.getStock(),
                ProductStatus.VAR,
                product.getUnitPrice()
        );
    }
}
