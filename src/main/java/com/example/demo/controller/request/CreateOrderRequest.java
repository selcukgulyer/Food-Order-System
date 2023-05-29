package com.example.demo.controller.request;

import com.example.demo.entities.OrderStatus;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    
    private OrderStatus status = OrderStatus.INITIAL;
    @NotNull(message = "piece field cannot be empty")
    private int piece;
    private User user;
    private Product product;
}
