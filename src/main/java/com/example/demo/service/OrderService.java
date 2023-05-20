package com.example.demo.service;

import com.example.demo.controller.request.CreateOrderRequest;
import com.example.demo.controller.request.UpdateOrderRequest;
import com.example.demo.controller.response.OrderResponse;
import com.example.demo.entities.Order;

import java.util.List;

public interface OrderService {
    OrderResponse add(CreateOrderRequest request);

    OrderResponse updateOrder(int id, UpdateOrderRequest request);

    Order findByIdOrder(int id);

    void deleteOrder(int id);

    List<OrderResponse> getAll();
}
