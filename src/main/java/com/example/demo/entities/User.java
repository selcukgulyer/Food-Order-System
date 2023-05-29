package com.example.demo.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birth;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CardDetails> cards = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> order = new ArrayList<>();

    public User(String name, String lastName, LocalDate birth, int age, List<CardDetails> cards, List<Order> order) {
        this.name = name;
        this.lastName = lastName;
        this.birth = birth;
        this.age = age;
        this.cards = cards;
        this.order = order;
    }


}




