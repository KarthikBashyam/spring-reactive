package com.example.reactive;

import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SynchronousSink;

public class ReactiveTest {

	public static void main(String[] args) {

		//generate can publish only one event.
		Flux<Integer> feed = Flux.<Integer>generate(generator -> emit(generator));
		feed.log().subscribe(System.out::println);
		
		//create can publish multiple events.
		Flux<Integer> feedSynch = Flux.<Integer>create(emitter -> emitAsynch(emitter));
		feedSynch.subscribe(ReactiveTest::print);
		sleep(1000);
		
	}

	public static void print(int i) {
		sleep(500);
		System.out.println(i);
	}
	
	private static boolean sleep(int i) {
		try {
			TimeUnit.MILLISECONDS.sleep(i);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}

	private static void emitAsynch(FluxSink<Integer> emitter) {
		for(int i = 1; i<=10; i++) {
			emitter.next(i);
			sleep(100);
		}
		emitter.complete();
	}

	private static void emit(SynchronousSink<Integer> generator) {

		for (int i = 1; i <= 1; i++) {
			generator.next(i);
		}
		generator.complete();
	}

}
