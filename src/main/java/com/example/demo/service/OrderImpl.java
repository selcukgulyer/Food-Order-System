package com.example.demo.service;

import com.example.demo.controller.request.CreateOrderRequest;
import com.example.demo.controller.request.UpdateOrderRequest;
import com.example.demo.controller.response.OrderResponse;
import com.example.demo.entities.Order;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.event.CreatedOrderEvent;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final AmqpTemplate rabbitTemplate;
    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;
    @Value("${rabbitmq.queue.order_created_product}")
    String orderProduct;

    @Override
    public OrderResponse add(CreateOrderRequest request) {
        User user = userService.getByUser(request.getUser().getId());
        Product product = productService.getByProductId(request.getProduct().getId());

        Order order = new Order(
                request.getStatus(),
                request.getPiece(),
                0,
                user,
                product);

        orderRepository.save(order);

        CreatedOrderEvent event = CreatedOrderEvent.builder().
                orderId(order.getId())
                .productId(product.getId())
                .build();

        rabbitTemplate.convertAndSend(orderProduct, event);

        return OrderResponse.from(order);
    }

    @Override
    public OrderResponse updateOrder(int id, UpdateOrderRequest request) {
        Order byIdOrder = findByIdOrder(id);
        Order order = new Order(
                byIdOrder.getId(),
                request.getStatus(),
                request.getPiece(),
                request.getTotal(),
                byIdOrder.getUser(),
                byIdOrder.getProduct()

        );
        orderRepository.save(order);
        return OrderResponse.from(order);

    }

    @Override
    public Order findByIdOrder(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }
        throw new AsgDataNotFoundException(ExceptionType.ORDER_DATA_NOT_FOUND);
    }

    @Override
    public void deleteOrder(int id) {
        findByIdOrder(id);
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderResponse> getAll() {
        List<Order> orderList = orderRepository.findAll();
        return OrderResponse.convertToList(orderList);
    }

}

