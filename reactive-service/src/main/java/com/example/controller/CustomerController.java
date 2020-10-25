package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.domain.Customer;
import com.example.domain.CustomerOrders;
import com.example.domain.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

	private final Map<Integer, Customer> customers = Map.of(1, "Karthik", 2, "Bashyam").entrySet().stream().collect(
			Collectors.toConcurrentMap(Map.Entry::getKey, entry -> new Customer(entry.getKey(), entry.getValue())));

	@Autowired
	WebClient webClient;

	@GetMapping("/customers")
	public Flux<Customer> getCustomers(@RequestParam(name = "ids") Integer[] ids) {

		var custStream = customers.values().stream();

		var idsList = List.of(ids);

		List<Customer> custList = custStream.filter(cust -> idsList.contains(cust.getId()))
				.collect(Collectors.toList());

		return Flux.fromIterable(custList);
	}

	@GetMapping("/custorders")
	public Flux<CustomerOrders> getCustomerOrders() {

		var ids = new String[] { "1", "2" };
		var customerURL = "http://localhost:9000/customers?ids=" + String.join(",", ids);
		var ordersURL = "http://localhost:9000/orders?ids=" + String.join(",", ids);

		Flux<Customer> customerList = webClient.get().uri(customerURL).retrieve().bodyToFlux(Customer.class);
		Flux<Order> orders = webClient.get().uri(ordersURL).retrieve().bodyToFlux(Order.class);

		Flux<CustomerOrders> customerOrders = customerList.flatMap(cust -> {

			Mono<List<Order>> filteredOrders = orders.filter(order -> order.getCustomerId().equals(cust.getId()))
					.collectList();

			return Flux.zip(Mono.just(cust), filteredOrders);
		}).map(tuple -> new CustomerOrders(tuple.getT1(), tuple.getT2()));

		return customerOrders;

	}

}
