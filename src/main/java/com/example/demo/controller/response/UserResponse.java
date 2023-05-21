package com.example.demo.controller.response;

import com.example.demo.entities.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private String name;
    private String lastName;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birth;
    private int age;
    private List<CardResponse> cards;


    /* public List<UserDto> convert(List<User> users){
                return users.stream()
                        .map(user -> UserDto.from(user))
                        .collect(Collectors.toList());
            }*/


    public static UserResponse from(User user) {
        return new UserResponse(
                user.getName(),
                user.getLastName(),
                user.getBirth(),
                user.getAge(),
                CardResponse.convert(user.getCards())
        );
    }


}



