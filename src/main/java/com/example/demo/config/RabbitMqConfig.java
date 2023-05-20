package com.example.demo.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${sample.rabbitmq.exchange}")
    String exchange;
    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;
    @Value("${rabbitmq.queue.order_created}")
    String orderCreatedQueueName;
    @Value("${rabbitmq.queue.order_created_product}")
    String orderProduct;
    @Value("${rabbitmq.queue.order_validate}")
    String orderValidate;

    @Bean("rabbitListenerContainerFactory")
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setDefaultRequeueRejected(false);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue queue() {
        return new Queue(orderCreatedQueueName, true);
    }


    @Bean
    Queue queue2() {
        return new Queue(orderProduct, true);
    }

    @Bean
    Queue queue3() {
        return new Queue(orderValidate, true);
    }


    @Bean
    Binding routing(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
