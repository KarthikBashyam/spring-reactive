package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Order;

import reactor.core.publisher.Flux;

@RestController
public class OrderController {
	
	
	private final Map<Integer, List<Order>> orders = IntStream.range(0, 3).boxed()
			.collect(Collectors.toConcurrentMap(custId -> Integer.valueOf(custId), custId -> {
				var orders = new CopyOnWriteArrayList<Order>();
				for (int i = 1; i < 3; i++) {
					orders.add(new Order(UUID.randomUUID().toString(), custId));
				}
				return orders;
			}));
	
	@GetMapping("/orders")
	public Flux<Order> getOrders(@RequestParam(value = "ids") Integer[] ids) {
		
		var custIds = List.of(ids);
		return Flux.fromStream(orders.entrySet().stream()
				.filter(entry -> custIds.contains(entry.getKey())).flatMap(entry -> entry.getValue().stream()));
		
	}
	

}
