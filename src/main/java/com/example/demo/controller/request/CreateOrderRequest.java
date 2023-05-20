package com.example.demo.controller.request;

import com.example.demo.entities.OrderStatus;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private int id;
    private OrderStatus status = OrderStatus.YOK;
    private User user;
    private Product product;
}
