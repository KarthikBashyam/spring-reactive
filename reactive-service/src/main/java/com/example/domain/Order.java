package com.example.domain;

public class Order {

	private String id;

	private Integer customerId;

	public Order(String id, Integer customerId) {
		super();
		this.id = id;
		this.customerId = customerId;
	}

	public Order() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
