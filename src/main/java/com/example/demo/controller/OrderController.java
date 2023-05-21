package com.example.demo.controller;


import com.example.demo.controller.request.CreateOrderRequest;
import com.example.demo.controller.request.UpdateOrderRequest;
import com.example.demo.controller.response.OrderResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<OrderResponse> add(@RequestBody CreateOrderRequest request) {

        return new ResponseEntity<>(orderService.add(request),HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable int id, @RequestBody UpdateOrderRequest request) {
        return new ResponseEntity<>(orderService.updateOrder(id, request),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResponse>> getAllOrder() {

        return new ResponseEntity<>(orderService.getAll(),HttpStatus.OK);
    }

}
