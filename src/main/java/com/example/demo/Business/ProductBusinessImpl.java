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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductBusinessImpl implements ProductBusiness {

    private final ProductService productService;
    private final OrderService orderService;
    private final AmqpTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;
    @Value("${rabbitmq.queue.order_validate}")
    String orderValidate;

    @Override
    public void controlStock(CreatedOrderEvent event) {

        Optional<Product> productOptional = productRepository.findById(event.getProductId());
        Order orderOptional = orderService.findByIdOrder(event.getOrderId());
        if (productOptional.isPresent()) {
            if (productOptional.get().getStock() > 0) {
                rabbitTemplate.convertAndSend(orderValidate, event);
            } else {
                // Todo : orderStatus rejecteda çekilecek
                // Todo : öncesinde orderin statusu rejecteda çekildiğine dair log atılacak
                log.info("orderstatus is rejected");
                Order order = new Order(
                        orderOptional.getId(),
                        OrderStatus.REJECTED,
                        orderOptional.getUser(),
                        orderOptional.getProduct()
                );
                orderRepository.save(order);
            }

        } else {
            // todo order rejecteda çekilecek log atılacak
            log.info("orderstatus is rejected");
            Order order = new Order(
                    orderOptional.getId(),
                    OrderStatus.REJECTED,
                    orderOptional.getUser(),
                    orderOptional.getProduct()
            );
            orderRepository.save(order);
        }

    }

    // Todo : orderserviceden order id var mı kontrol et ?
    // Todo : status adını inital,Rejected,Approved
    @Override
    public void updateOrder(CreatedOrderEvent event) {
        System.out.println("Kuyruktan gelen mesaj  :" + event);
        Product productOptional = productService.getByProductId(event.getProductId());

        int stock = productOptional.getStock() - 1;
        Product product = new Product(
                productOptional.getId(),
                productOptional.getProductName(),
                stock,
                productOptional.getProductStatus(),
                productOptional.getUnitPrice(),
                productOptional.getOrder()
        );
        productRepository.save(product);
        Order orderOptional = orderService.findByIdOrder(event.getOrderId());
        Order order = new Order(
                orderOptional.getId(),
                OrderStatus.APPROVED,
                orderOptional.getUser(),
                orderOptional.getProduct()
        );
        orderRepository.save(order);
    }

}





