package com.example.demo.service;

import com.example.demo.controller.request.CreateUserRequest;
import com.example.demo.controller.request.UpdateUserRequest;
import com.example.demo.controller.response.UserCreateResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.controller.response.UserUpdateResponse;
import com.example.demo.entities.User;
import com.example.demo.exception.AsgDataNotFoundException;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UserServiceTest {
    private UserRepository userRepository;
    private AmqpTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        rabbitTemplate = Mockito.mock(AmqpTemplate.class);
        userService = new UserImpl(userRepository, rabbitTemplate);
    }

    @Test
    void testCreateUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("test");
        createUserRequest.setLastName("test-last");
        createUserRequest.setAge(12);
        createUserRequest.setBirth(LocalDate.of(1999, 12, 12));
        UserCreateResponse userResponse = new UserCreateResponse("test", "test-last", LocalDate.of(1999, 12, 12), 12);
        User savedUser = new User(1, "test", "test-last", LocalDate.of(1999, 12, 12), 12, null, null);
        User user = objectMapper.convertValue(createUserRequest, User.class);

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        UserCreateResponse response = userService.createUser(createUserRequest);

        assertEquals(response, userResponse);

        Mockito.verify(userRepository).save(user);

    }


    @Test
    public void testDeleteUser() {
        int id = 1;
        User user = new User(id, "test", "test-last", LocalDate.of(1999, 12, 12), 12, null, null);

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteUser(id);
        Mockito.verify(userRepository).findById(id);
        Mockito.verify(userRepository).deleteById(id);
    }


    @Test
    public void testDeleteUser_whenUserIdDoesNotExists_itShouldThrowsUserNotFoundException() {
        int id = 1;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(AsgDataNotFoundException.class, () ->
                userService.getByUser(id));

        Mockito.verify(userRepository).findById(id);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testUpdateUser() {
        int id = 1;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(id);
        request.setName("test2");
        request.setLastName("test-last2");
        request.setAge(12);
        request.setBirth(LocalDate.of(1999, 12, 12));

        User user = new User(id, "test", "test-last", LocalDate.of(1999, 12, 12), 12, null, null);
        User updateUser = new User(id, "test2", "test-last2", LocalDate.of(1999, 12, 12), 12, null, null);
        User savedUser = new User(id, "test2", "test-last2", LocalDate.of(1999, 12, 12), 12, null, null);
        UserUpdateResponse userResponse = new UserUpdateResponse(request.getName(), request.getLastName(), request.getBirth(), request.getAge());

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(updateUser)).thenReturn(savedUser);

        UserUpdateResponse response = userService.updateUser(id, request);
        assertEquals(response, userResponse);

        Mockito.verify(userRepository).findById(id);
        Mockito.verify(userRepository).save(updateUser);
    }

    @Test
    void testUpdateUser_whenUserIdDoesNotExists_itShouldThrowsUserNotFoundException() {
        int id = 1;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(id);
        request.setName("test2");
        request.setLastName("test-last2");
        request.setAge(12);
        request.setBirth(LocalDate.of(1999, 12, 12));

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class, () ->
                userService.getByUser(id));

        Mockito.verify(userRepository).findById(id);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetAllUser() {
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "test-1", "test-last-1", LocalDate.of(1999, 12, 12), 11, null, null);
        User user2 = new User(2, "test-2", "test-last-2", LocalDate.of(1999, 12, 12), 12, null, null);
        User user3 = new User(3, "test-3", "test-last-3", LocalDate.of(1999, 12, 12), 13, null, null);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        List<UserResponse> userResponseList = userList.stream().map(UserResponse::from)
                .collect(Collectors.toList());

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<UserResponse> userResponses = userService.getAll();

        assertEquals(userResponses, userResponseList);

        Mockito.verify(userRepository).findAll();
    }

    @Test
    public void getByUser() {
        int id = 1;
        User user = new User(1, "test-1", "test-last-1", LocalDate.of(1999, 12, 12), 11, null, null);
        User userResponse = new User(id, "test-1", "test-last-1", LocalDate.of(1999, 12, 12), 11, null, null);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User response = userService.getByUser(id);

        assertEquals(response, userResponse);
        Mockito.verify(userRepository).findById(id);
    }

    @Test
    public void getByUser_whenUserIdDoesNotExists_itShouldThrowsUserNotFoundException() {
        int id = 1;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class, () -> userService.getByIdUser(id));

        Mockito.verify(userRepository).findById(id);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetByIdUser() {
        int id = 1;
        User user = new User(1, "test-1", "test-last-1", LocalDate.of(1999, 12, 12), 11, null, null);
        UserResponse userResponse = new UserResponse("test-1", "test-last-1", LocalDate.of(1999, 12, 12), 11, new ArrayList<>());
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponse response = userService.getByIdUser(id);

        assertEquals(userResponse, response);

        Mockito.verify(userRepository).findById(id);

    }

    @Test
    public void testGetByIdUser_whenUserIdDoesNotExists_itShouldThrowsUserNotFoundException() {
        int id = 1;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AsgDataNotFoundException.class, () -> userService.getByIdUser(id));

        Mockito.verify(userRepository).findById(id);
        Mockito.verifyNoMoreInteractions(userRepository);

    }
}