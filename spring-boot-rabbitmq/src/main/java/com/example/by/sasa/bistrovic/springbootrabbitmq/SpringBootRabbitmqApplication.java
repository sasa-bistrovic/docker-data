package com.example.by.sasa.bistrovic.springbootrabbitmq;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRabbitmqApplication {
    
        @Value("${SPRING_RABBITMQ_HOST}")
        private static String springRabbitmqHost;

	public static void main(String[] args) {
                //Map<String, String> map = System.getenv();
                System.out.println(System.getenv("SPRING_RABBITMQ_HOST"));
                System.out.println(System.getenv("SPRING_RABBITMQ_PORT"));
                System.out.println(System.getenv("SPRING_RABBITMQ_USERNAME"));
                System.out.println(System.getenv("SPRING_RABBITMQ_PASSWORD"));
		SpringApplication.run(SpringBootRabbitmqApplication.class, args);
	}

}
