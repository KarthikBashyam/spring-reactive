package com.example.practice;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class PublishOnSubscribeOnExample {

	//Blocking API
	private static RestTemplate restTemplate = new RestTemplate();
	
	//Non-Blocking API
	private static WebClient webClient = WebClient.builder().build(); 
	
	
	public static void main(String[] args) throws InterruptedException {
		
		Flux<String> flux = Flux.just("hello","world","karthik","bashyam").map(PublishOnSubscribeOnExample::convertString);
		//flux.subscribe(System.out::println);
		
		//Schedulers.elastic() is used for I/O bound tasks. 
		//It's a Thread pool that grows on demand and shuts down the thread after completing the task.		
		//Schedulers.parallel() is used for CPU bound tasks. Its limited to the number of cores.
		
		//publishOn will affect the chain down the line.
		//flux.publishOn(Schedulers.elastic()).subscribe(s -> System.out.println("Received "+ s + " on thread :" + Thread.currentThread().getName()));
		
		//subscribeOn will propagate to the source. Hence convertString method also will print elastic thread name
		//flux.subscribeOn(Schedulers.elastic()).subscribe(s -> System.out.println("Received "+ s + " on thread :" + Thread.currentThread().getName()));
		
		//The Web Service call is blocking and the two lines will execute synchronously.  
		//Flux.range(0, 3).map(num -> callWebService(num)).subscribe(s -> print(s));		
		//Flux.range(4, 3).map(num -> callWebService(num)).subscribe(s -> print(s));
		
		//The Web Service call is non-blocking and the two lines will execute asynchronously.  
		//Flux.range(0, 3).flatMap(num -> callWebServiceNB(num)).subscribe(s -> print(s));		
		//Flux.range(4, 3).flatMap(num -> callWebServiceNB(num)).subscribe(s -> print(s));
		
		
		//Use publishOn to make the blocking webservice calls asynchronous. 
		//Flux.range(0, 3).publishOn(Schedulers.elastic()).map(num -> callWebService(num)).subscribe(s -> print(s));
		//Flux.range(4, 3).publishOn(Schedulers.elastic()).map(num -> callWebService(num)).subscribe(s -> print(s));
		
		//What if the range function was written by somebody. We can make it asynchronous.
		getRange(0,3).subscribeOn(Schedulers.boundedElastic()).subscribe(s ->print(s));
		getRange(4,3).subscribeOn(Schedulers.boundedElastic()).subscribe(s ->print(s));
		
		
		
		TimeUnit.SECONDS.sleep(3);		
		
	}
	
	
	private static Flux<String> getRange(int start , int count) {
		//There is no publishOn. Its a blocking call.
		return Flux.range(start,count).map(num -> callWebService(num));
	}

	private static void print(String s) {
		System.out.println("Received String " +s + " in thread : " + Thread.currentThread().getName());	
	}
	
	private static String convertString(String s) {
		System.out.println("Converting String " +s + " in thread : " + Thread.currentThread().getName());
		return s.toUpperCase();
	}
	
	private static String callWebService(int num) {
		System.out.println("Calling webservice " + num + " using thread :" + Thread.currentThread().getName());
		Map<String,Integer> params = Map.of("num",num);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9000/nums/{num}", HttpMethod.GET, null,
				new ParameterizedTypeReference<String>() {
				}, params);

		return response.getBody();
	}
	
	private static Mono<String> callWebServiceNB(int num) {
		System.out.println("Calling webservice " + num + " using thread :" + Thread.currentThread().getName());
		Map<String,Integer> params = Map.of("num",num);
		return webClient.get().uri("http://localhost:9000/nums/{num}",params).retrieve().bodyToMono(String.class);
		
	}

}
