package com.example.demo.service;

import com.example.demo.controller.request.CreateUserRequest;
import com.example.demo.controller.request.UpdateUserRequest;
import com.example.demo.controller.response.UserCreateResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.controller.response.UserUpdateResponse;
import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    UserCreateResponse createUser(CreateUserRequest request);

    void deleteUser(int id);

    UserUpdateResponse updateUser(int id, UpdateUserRequest req);

    List<UserResponse> getAll();

    UserResponse getByIdUser(int id);

    User getByUser(int id);
}





