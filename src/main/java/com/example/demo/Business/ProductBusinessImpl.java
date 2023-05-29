package com.example.demo.Business;

import com.example.demo.entities.Order;
import com.example.demo.entities.OrderStatus;
import com.example.demo.entities.Product;
import com.example.demo.event.CreatedOrderEvent;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductBusinessImpl implements ProductBusiness {

    private final ProductService productService;
    private final OrderService orderService;
    private final AmqpTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;
    @Value("${rabbitmq.queue.order_validate}")
    String orderValidate;


    @Override
    public void controlStock(CreatedOrderEvent event) {

        Product productOptional = productService.getByProductId(event.getProductId());
        Order orderOptional = orderService.findByIdOrder(event.getOrderId());
        int count = productOptional.getStock() - orderOptional.getPiece();
        if (productOptional.getStock() > orderOptional.getPiece() && count > 0) {
            //  rabbitTemplate.convertAndSend(orderValidate, event);
            kafkaTemplate.send("default", event);
        } else {
            double total = productOptional.getUnitPrice() * orderOptional.getPiece();
            log.info("orderstatus is rejected");
            Order order = new Order(
                    orderOptional.getId(),
                    OrderStatus.REJECTED,
                    orderOptional.getPiece(),
                    total,
                    orderOptional.getUser(),
                    orderOptional.getProduct()
            );
            orderRepository.save(order);
        }

    }

    @Override
    public void updateOrder(CreatedOrderEvent event) {
        Product productOptional = productService.getByProductId(event.getProductId());
        Order orderOptional = orderService.findByIdOrder(event.getOrderId());

        int stock = productOptional.getStock() - orderOptional.getPiece();
        Product product = new Product(
                productOptional.getId(),
                productOptional.getProductName(),
                stock,
                productOptional.getProductStatus(),
                productOptional.getUnitPrice(),
                productOptional.getOrder()
        );
        productRepository.save(product);
        double total = productOptional.getUnitPrice() * orderOptional.getPiece();
        Order order = new Order(
                orderOptional.getId(),
                OrderStatus.APPROVED,
                orderOptional.getPiece(),
                total,
                orderOptional.getUser(),
                orderOptional.getProduct()
        );
        orderRepository.save(order);
    }

}





