package com.by.sasa.bistrovic.springbootkafka;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/messages")
public class ItemsController {
    
    private int MAX_RECORDS_TO_PROCESS = 1;
    
    private String myTimerStr;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
@GetMapping
public List<String> getAllMessages() {
    long currentTimeMillis = Instant.now().toEpochMilli();
        
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, String.valueOf(currentTimeMillis));
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("SPRING_KAFKA_URL"));
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    StreamsBuilder builder = new StreamsBuilder();
    KStream<String, String> logStream = builder.stream(System.getenv("SPRING_KAFKA_TOPIC"));

    List<String> receivedMessages = new ArrayList<>();
    CountDownLatch latch = new CountDownLatch(1); 

    logStream.foreach((key, value) -> {
        receivedMessages.add(value);
        latch.countDown();
    });

    KafkaStreams streams = new KafkaStreams(builder.build(), props);
    streams.start();

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        streams.close();
    }));

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // Postavljamo "receivedMessages" kao rezultat metode
            myTimerStr = "2"; // Ovo se čini nepotrebno, jer ne koristimo myTimerStr nigdje drugdje
            timer.cancel(); // Otkazujemo timer jer smo dobili rezultat
        }
    };
    timer.schedule(task, 6000); // Pokreni timer nakon 6 sekundi
    
    try {
        latch.await(); // Čekaj da se sve poruke obrade
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    try {
    // Sleep for 1 second (1000 milliseconds)
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // Handle the InterruptedException if necessary
    e.printStackTrace();
}

    return receivedMessages; // Vratimo receivedMessages kao rezultat metode
}


    @PostMapping
    public void addItem(@RequestBody Item item) {
        final var topic = System.getenv("SPRING_KAFKA_TOPIC");

        final Map<String, Object> config =
                Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        System.getenv("SPRING_KAFKA_URL"),
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        StringSerializer.class.getName(),
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        StringSerializer.class.getName()); // Serializer for value is missing

        try (var producer = new KafkaProducer<String, String>(config)) {
            producer.send(new ProducerRecord<>(topic, "myKey", item.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
