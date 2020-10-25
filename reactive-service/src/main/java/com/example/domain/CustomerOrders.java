package com.example.domain;

import java.util.List;

public class CustomerOrders {

	private Customer customer;

	private List<Order> orders;

	public CustomerOrders(Customer customer, List<Order> orders) {
		super();
		this.customer = customer;
		this.orders = orders;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
