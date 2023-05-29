package com.example.demo.controller.request;

import com.example.demo.entities.CardDetails;
import com.example.demo.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "name field cannot be empty")
    private String name;
    @NotBlank(message = "lastName field cannot be empty")
    private String lastName;
    @NotNull(message = "age field cannot be empty")
    private int age;
    @NotNull(message = "birth field cannot be empty")
    private LocalDate birth;
    private List<CardDetails> cards;
    private List<Order> order;

   /* public static User deneme(CreateRequest request){
        return new User(
                request.getId(),
                request.getName(),
                request.getLastName(),
                request.getBirth(),
                request.getAge()

        );
    }*/
}
