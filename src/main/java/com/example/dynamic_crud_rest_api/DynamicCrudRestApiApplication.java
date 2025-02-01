package com.example.dynamic_crud_rest_api;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DynamicCrudRestApiApplication {

	private final CustomUpdateSpecificationsTest customUpdateSpecificationsTest;

	@PostConstruct
	public void init() {
		customUpdateSpecificationsTest.test();
	}

	public static void main(String[] args) {
		SpringApplication.run(DynamicCrudRestApiApplication.class, args);
	}

}

