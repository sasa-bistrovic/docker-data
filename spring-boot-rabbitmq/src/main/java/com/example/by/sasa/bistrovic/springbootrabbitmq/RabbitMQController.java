package com.example.by.sasa.bistrovic.springbootrabbitmq;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.Queue;

@RestController
@RequestMapping("/api/messages")
public class RabbitMQController {

    private final RabbitTemplate rabbitTemplate;
    private final Queue myQueue;

    @Autowired
    public RabbitMQController(RabbitTemplate rabbitTemplate, Queue myQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.myQueue = myQueue;
    }

    @PostMapping
    public void sendMessage(@RequestBody Item item) {
        rabbitTemplate.convertAndSend(myQueue.getName(), item.getMessage());
    }

    @GetMapping
    public List<String> receiveAllMessages() {
        List<String> messages = new ArrayList<>();
        Object message = rabbitTemplate.receiveAndConvert(myQueue.getName());
        while (message != null) {
            messages.add(message.toString());
            message = rabbitTemplate.receiveAndConvert(myQueue.getName());
        }
        
        for (int i=0; i<messages.size(); i++) {
            rabbitTemplate.convertAndSend(myQueue.getName(), messages.get(i));
        }

        return messages;
    }
    
}
