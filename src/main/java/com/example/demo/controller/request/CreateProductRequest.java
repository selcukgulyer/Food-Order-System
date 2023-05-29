package com.example.demo.controller.request;

import com.example.demo.entities.Order;
import com.example.demo.entities.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Product name field cannot be empty")
    private String productName;
    @NotNull(message = "stock field cannot be empty")
    @Min(0)
    private int stock;
    private ProductStatus productStatus = ProductStatus.VAR;
    @NotNull(message = "Unit price field cannot be empty")
    private Double unitPrice;
    private List<Order> order;
}
