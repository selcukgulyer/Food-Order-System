package com.example.demo.Consumer;

import com.example.demo.Business.ProductBusiness;
import com.example.demo.controller.response.UserCreateResponse;
import com.example.demo.event.CreatedOrderEvent;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {

    // Todo : diğer metorlara ack eklenecek
    private final ProductBusiness productBusiness;


    @RabbitListener(queues = "${rabbitmq.queue.order_created}")
    public void consumerMessageFromQueue(UserCreateResponse user) {
        System.out.println("Kuyruktan gelen mesaj :" + user);
    }

    @RabbitListener(queues = "${rabbitmq.queue.order_created_product}")
    public void productMessage(CreatedOrderEvent event, Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            productBusiness.controlStock(event);
        } catch (Exception e) {
            log.info("exception throwed when stock control to product, exception: {} ", e.getMessage());
        } finally {
            channel.basicAck(tag, false);
        }
    }


    @RabbitListener(queues = "${rabbitmq.queue.order_validate}")
    public void consumerMessageFromQueue2(CreatedOrderEvent event) {
        productBusiness.updateOrder(event);
    }


}
