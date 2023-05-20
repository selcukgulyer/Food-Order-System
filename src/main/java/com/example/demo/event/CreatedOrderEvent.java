package com.example.demo.event;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatedOrderEvent {
    private int orderId;
    private int productId;
}
