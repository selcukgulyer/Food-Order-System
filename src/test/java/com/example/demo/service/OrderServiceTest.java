package com.example.demo.service;

import com.example.demo.controller.request.CreateOrderRequest;
import com.example.demo.controller.request.UpdateOrderRequest;
import com.example.demo.controller.response.OrderResponse;
import com.example.demo.controller.response.ProductResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.entities.*;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.repository.OrderRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private UserService userService;
    private ProductService productService;
    private AmqpTemplate rabbitTemplate;
    private OrderService orderService;
    @BeforeEach
    void setUp(){
        orderRepository = Mockito.mock(OrderRepository.class);
        userService = Mockito.mock(UserService.class);
        productService = Mockito.mock(ProductService.class);
        rabbitTemplate = Mockito.mock(AmqpTemplate.class);
        orderService = new OrderImpl(orderRepository,userService,productService,rabbitTemplate);
    }
    @Test
    void add() {
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, new ArrayList<>(), null);
        User responseUser = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, new ArrayList<>(), null);
        Product product = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);
        Product responseProduct = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);
        UserResponse userResponse = new UserResponse(user.getName(), user.getLastName(),user.getBirth(), user.getAge(),new ArrayList<>());
        ProductResponse productResponse = new ProductResponse(product.getProductName(),product.getStock(),product.getProductStatus(),product.getUnitPrice());

        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setPiece(12);
        orderRequest.setStatus(OrderStatus.INITIAL);
        orderRequest.setUser(user);
        orderRequest.setProduct(product);

        Order order = new Order(orderRequest.getStatus(), orderRequest.getPiece(),0,orderRequest.getUser(),orderRequest.getProduct());
        Order order1 = new Order(1,OrderStatus.INITIAL,12,0,user,product);

        Mockito.when(userService.getByUser(user.getId())).thenReturn(responseUser);
        Mockito.when(productService.getByProductId(product.getId())).thenReturn(responseProduct);
        Mockito.when(orderRepository.save(order)).thenReturn(order1);
        OrderResponse orderResponse = new OrderResponse(0,OrderStatus.INITIAL,12,0,userResponse,productResponse);
        OrderResponse response = orderService.add(orderRequest);
        assertEquals(response,orderResponse);

        Mockito.verify(userService).getByUser(user.getId());
        Mockito.verify(productService).getByProductId(product.getId());
        Mockito.verify(orderRepository).save(order);

    }

    @Test
    void updateOrder() {
        int id=1;
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, new ArrayList<>(), null);
        Product product = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);

        UserResponse userResponse = new UserResponse(user.getName(), user.getLastName(),user.getBirth(), user.getAge(),new ArrayList<>());
        ProductResponse productResponse = new ProductResponse(product.getProductName(),product.getStock(),product.getProductStatus(),product.getUnitPrice());

        Order order = new Order(1,OrderStatus.INITIAL,12,0,user,product);
        UpdateOrderRequest orderRequest = new UpdateOrderRequest();
        orderRequest.setPiece(15);
        orderRequest.setStatus(OrderStatus.INITIAL);
        orderRequest.setTotal(0);
        Order updateOrder = new Order(id,OrderStatus.INITIAL,orderRequest.getPiece(),orderRequest.getTotal(),user,product);
        Order savedOrder = new Order(1,OrderStatus.INITIAL,15,0,user,product);
        OrderResponse orderResponse = new OrderResponse(1,OrderStatus.INITIAL,15,0,userResponse,productResponse);

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(updateOrder)).thenReturn(savedOrder);

        OrderResponse response = orderService.updateOrder(id,orderRequest);

        assertEquals(response,orderResponse);

        Mockito.verify(orderRepository).findById(id);
        Mockito.verify(orderRepository).save(updateOrder);
    }

    @Test
    void updateOrder_whenOrderIdDoesNotExists_itShouldThrowsOrderNotFoundException() {
        int id=1;
        UpdateOrderRequest orderRequest = new UpdateOrderRequest();
        orderRequest.setPiece(15);
        orderRequest.setStatus(OrderStatus.INITIAL);
        orderRequest.setTotal(0);

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class,() ->orderService.updateOrder(id,orderRequest));

        Mockito.verify(orderRepository).findById(id);
        Mockito.verifyNoMoreInteractions(orderRepository);
    }
    @Test
    void findByIdOrder() {
        int id=1;
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, new ArrayList<>(), null);
        Product product = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);

        Order order = new Order(1,OrderStatus.INITIAL,12,0,user,product);
        Order orderResponse = new Order(1,OrderStatus.INITIAL,12,0,user,product);

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Order response=orderService.findByIdOrder(id);

        assertEquals(response,orderResponse);

        Mockito.verify(orderRepository).findById(id);
    }

    @Test
    void findByIdOrder_whenOrderIdDoesNotExists_itShouldThrowsOrderNotFoundException() {
        int id=1;

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class,() -> orderService.findByIdOrder(id));

        Mockito.verify(orderRepository).findById(id);
    }

    @Test
    void deleteOrder() {
        int id=1;
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, new ArrayList<>(), null);
        Product product = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);

        Order order = new Order(1,OrderStatus.INITIAL,12,0,user,product);
        Order orderResponse = new Order(1,OrderStatus.INITIAL,12,0,user,product);

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        orderService.deleteOrder(id);
        Mockito.verify(orderRepository).findById(id);
        Mockito.verify(orderRepository).deleteById(id);

    }

    @Test
    void deleteOrder_whenOrderIdDoesNotExists_itShouldThrowsOrderNotFoundException() {
        int id=1;

        Mockito.when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class,() -> orderService.deleteOrder(id));

        Mockito.verify(orderRepository).findById(id);
        Mockito.verifyNoMoreInteractions(orderRepository);

    }

    @Test
    void getAll() {
        List<Order> orderList = new ArrayList<>();
        User user = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, new ArrayList<>(), null);
        Product product = new Product(1, "test", 1, ProductStatus.VAR, 123.0, null);

        User user2 = new User(2, "test2", "test-last2", LocalDate.of(1999, 12, 12), 12, new ArrayList<>(), null);
        Product product2 = new Product(2, "test2", 1, ProductStatus.VAR, 123.0, null);

        Order order1 = new Order(1,OrderStatus.INITIAL,12,0,user,product);
        Order order2 = new Order(2,OrderStatus.INITIAL,12,0,user2,product2);
        orderList.add(order1);
        orderList.add(order2);

        List<OrderResponse> response = OrderResponse.convertToList(orderList);

        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderResponse> orderResponses = orderService.getAll();

        assertEquals(response,orderResponses);

        Mockito.verify(orderRepository).findAll();

    }
}