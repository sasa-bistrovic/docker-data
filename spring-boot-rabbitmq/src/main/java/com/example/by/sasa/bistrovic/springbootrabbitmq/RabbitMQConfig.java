package com.example.by.sasa.bistrovic.springbootrabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Bean
    public Queue itemQueue() {
        return new Queue("My_First_Queue", true);
    }
}
