package com.example.practice;

import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class PublishOnSubscribeOnExample {
	
	public static void main(String[] args) throws InterruptedException {
		
		Flux<String> flux = Flux.just("hello","world","karthik","bashyam").map(PublishOnSubscribeOnExample::convertString);
		//flux.subscribe(System.out::println);
		
		//Schedulers.elastic() is used for I/O bound tasks. 
		//It's a Thread pool that grows on demand and shuts down the thread after completing the task.
		
		//Schedulers.parallel() is used for CPU bound tasks. Its limited to the number of cores.
		
		//publishOn will affect the chain down the line.
		//flux.publishOn(Schedulers.elastic()).subscribe(s -> System.out.println("Received "+ s + " on thread :" + Thread.currentThread().getName()));
		
		//subscribeOn will propagate to the source. Hence convertString method also will print elastic thread name
		flux.subscribeOn(Schedulers.elastic()).subscribe(s -> System.out.println("Received "+ s + " on thread :" + Thread.currentThread().getName()));
		
		TimeUnit.SECONDS.sleep(3);
		
	}
	
	private static String convertString(String s) {
		System.out.println("Converting String " +s + " in thread : " + Thread.currentThread().getName());
		return s.toUpperCase();
	}

}
