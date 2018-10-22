package com.example.reactiveservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReactiveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner startup() {
		return args -> {
			System.out.println("==== REACTIVE SERVICE STARTED ======");
		};
	}
}
