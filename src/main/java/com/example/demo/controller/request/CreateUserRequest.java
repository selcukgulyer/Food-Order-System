package com.example.demo.controller.request;

import com.example.demo.entities.CardDetails;
import com.example.demo.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private int id;
    private String name;
    private String lastName;
    private int age;
    // @JsonSerialize(using = LocalDateSerializer.class)
    //@JsonDeserialize(using = LocalDateDeserializer.class)
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
