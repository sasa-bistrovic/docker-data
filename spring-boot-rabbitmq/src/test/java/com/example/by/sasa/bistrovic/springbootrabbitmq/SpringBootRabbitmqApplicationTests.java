package com.example.by.sasa.bistrovic.springbootrabbitmq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootRabbitmqApplicationTests {

	@Test
	void contextLoads() {
                System.out.println(System.getenv("SPRING_RABBITMQ_HOST"));
                System.out.println(System.getenv("SPRING_RABBITMQ_PORT"));
                System.out.println(System.getenv("SPRING_RABBITMQ_USERNAME"));
                System.out.println(System.getenv("SPRING_RABBITMQ_PASSWORD"));		
	}

}
