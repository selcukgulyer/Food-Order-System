package com.example.demo.service;

import com.example.demo.controller.request.CreateUserRequest;
import com.example.demo.controller.request.UpdateUserRequest;
import com.example.demo.controller.response.UserCreateResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.entities.User;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;
    private final DirectExchange exchange;
    private final AmqpTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;
    @Value("${rabbitmq.queue.order_created}")
    String orderCreatedQueueName;

    @Override
    public UserCreateResponse createUser(CreateUserRequest request) {

        //User user = CreateRequest.deneme(request);

        User user = new User(request.getId(),
                request.getName(),
                request.getLastName(),
                request.getBirth(),
                request.getAge(),
                request.getCards(),
                request.getOrder()

        );


        userRepository.save(user);

        UserCreateResponse userCreateResponse = UserCreateResponse.from(user);

        //rabbitTemplate.convertAndSend(exchange.getName(),routingKey,userDto);
        rabbitTemplate.convertAndSend(orderCreatedQueueName, userCreateResponse);
        return userCreateResponse;
    }

    @Override
    public UserResponse deleteUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            System.out.println();
            User response = new User(
                    user.get().getId(),
                    user.get().getName(),
                    user.get().getLastName(),
                    user.get().getBirth(),
                    user.get().getAge(),
                    user.get().getCards(),
                    user.get().getOrder()
            );

            //  rabbitTemplate.convertAndSend(exchange.getName(), routingKey, response);
            userRepository.deleteById(id);
            return UserResponse.from(response);

        } else {
            throw new RuntimeException("Böyle bir kullanıcı bulunmamaktadır");

        }


    }

    @Override
    public UserResponse updateUser(int id, UpdateUserRequest req) {
        User byUser = getByUser(id);
        User user = new User(id, req.getName(), req.getLastName(), req.getBirth(), req.getAge(), req.getCardDetails(), req.getOrder());
        userRepository.save(user);
        return UserResponse.from(user);


    }

    @Override
    public List<UserResponse> getAll() {
        List<User> user = userRepository.findAll();
        return user.stream()
                .map(user1 -> UserResponse.from(user1))
                .collect(Collectors.toList());

    }

    public User getByUser(int id) {
        Optional<User> userResponse = userRepository.findById(id);
        if (userResponse.isPresent()) {
            return userResponse.get();
        }

        throw new AsgDataNotFoundException(ExceptionType.USER_DATA_NOT_FOUND, "Veritabanında böyle bir kayıt bulunamadı");

    }


    public UserResponse getByIdUser(int id) {
        Optional<User> response = userRepository.findById(id);
        if (response.isPresent()) {

            User user = new User(id,
                    response.get().getName(),
                    response.get().getLastName(),
                    response.get().getBirth(),
                    response.get().getAge(),
                    response.get().getCards(),
                    response.get().getOrder()
            );

            return UserResponse.from(user);

        } else {
            throw new RuntimeException("Hataaaaa");
        }

    }
}
