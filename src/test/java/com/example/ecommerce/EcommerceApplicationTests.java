package com.example.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // This tells Spring Boot to use application-test.properties
class EcommerceApplicationTests {

	@Test
	void contextLoads() {
	}

}
