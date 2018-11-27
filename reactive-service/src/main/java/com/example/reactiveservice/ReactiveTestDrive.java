package com.example.reactiveservice;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class ReactiveTestDrive {

	public static void main(String[] args) {

		// Cold Publisher
		Flux.just("Karthik", "Bashyam").log().subscribe(System.out::println);

		Flux<Integer> flux = Flux.<Integer>create(sink -> emit(sink));
		flux.subscribe(ReactiveTestDrive::print);

		sleep(10000);

	}

	private static boolean sleep(int i) {
		try {
			TimeUnit.MILLISECONDS.sleep(i);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	private static void emit(FluxSink<Integer> generator) {
		System.out.println("=========== INSIDE EMIT =================");
		IntStream.range(1, 10).forEach(num -> generator.next(num));
	}

	private static void print(Integer num) {
		System.out.println("Printing :" + num + "-" + Thread.currentThread());
	}
}
