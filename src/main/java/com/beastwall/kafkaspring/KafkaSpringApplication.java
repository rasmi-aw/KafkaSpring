package com.beastwall.kafkaspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@RestController("/")
public class KafkaSpringApplication implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringApplication.class, args);
        //
    }

    @Override
    public void run(String... args) throws Exception {
    }

    @GetMapping("")
    public void sendMessage(@RequestParam(required = false) String message) {
        System.out.println(message);
        kafkaTemplate.send("spring-kafka", message);

    }

    @KafkaListener(topics = "spring-kafka", groupId = "group-spring-kafka")
    public void readMessage(String message) {
        System.out.println("received message consumer " + message);
    }
}
