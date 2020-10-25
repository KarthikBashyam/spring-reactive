package com.example.reactiveservice;

import reactor.core.publisher.Flux;

public class ReactorTestDrive {

	public static void main(String[] args) {

		Flux<Integer> range = Flux.from(test());
		
		// StepVerifier.create(range).expectNextCount(10).verifyComplete();

		/*
		 * range.doOnNext(num ->
		 * System.out.println("TEST NEXT")).subscribe(System.out::println);
		 * range.doOnNext(num ->
		 * System.out.println("TEST NEXT1")).subscribe(System.out::println);
		 * range.doOnNext(num ->
		 * System.out.println("TEST NEXT2")).subscribe(System.out::println);
		 */
		

	}

	private static Flux test() {
		System.out.println("======== INSIDE PUBLISHER =============");
		return Flux.range(0, 10);
	}

}
