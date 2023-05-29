package com.example.demo.controller.response;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderStatus;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderResponse {
    private int id;
    private OrderStatus order;
    private int piece;
    private double total;
    private UserResponse userResponse;
    private ProductResponse productResponse;

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                order.getPiece(),
                order.getTotal(),
                UserResponse.from(order.getUser()),
                ProductResponse.from(order.getProduct())
        );
    }

    public static List<OrderResponse> convertToList(List<Order> orders){
        return orders.stream()
                .map(order-> OrderResponse.from(order))
                .collect(Collectors.toList());
    }
}
