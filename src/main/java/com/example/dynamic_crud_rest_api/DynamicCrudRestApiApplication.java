package com.example.dynamic_crud_rest_api;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DynamicCrudRestApiApplication {

	private final CustomUpdateAndDeleteSpecificationsTest customUpdateAndDeleteSpecificationsTest;

	@PostConstruct
	public void init() {
		customUpdateAndDeleteSpecificationsTest.test();
	}

	public static void main(String[] args) {
		SpringApplication.run(DynamicCrudRestApiApplication.class, args);
	}

}

