package com.example.practice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class TestController {
	
	@GetMapping("/nums/{num}")
	public Flux<String> num(@PathVariable("num") int num) {
		return Flux.just("Hello Number:"+num);
	}

}
