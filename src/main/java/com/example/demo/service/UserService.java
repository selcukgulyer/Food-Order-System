package com.example.demo.service;

import com.example.demo.controller.request.CreateUserRequest;
import com.example.demo.controller.request.UpdateUserRequest;
import com.example.demo.controller.response.UserCreateResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    UserCreateResponse createUser(CreateUserRequest request);

    UserResponse deleteUser(int id);

    UserResponse updateUser(int id, UpdateUserRequest req);

    List<UserResponse> getAll();

    UserResponse getByIdUser(int id);

    User getByUser(int id);
}





