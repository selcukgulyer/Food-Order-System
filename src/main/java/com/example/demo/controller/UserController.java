package com.example.demo.controller;

import com.example.demo.controller.request.CreateUserRequest;
import com.example.demo.controller.request.UpdateUserRequest;
import com.example.demo.controller.response.UserCreateResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public UserCreateResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);

    }

    @DeleteMapping("/{id}")
    public UserResponse deleteUser(@PathVariable int id) {

        return userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public UserResponse updateUser(@PathVariable int id, @RequestBody UpdateUserRequest req) {
        return userService.updateUser(id, req);
    }

    @GetMapping("/getAll")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getByIdUser(@PathVariable int id) {

        return ResponseEntity.ok(userService.getByIdUser(id));
    }
}
