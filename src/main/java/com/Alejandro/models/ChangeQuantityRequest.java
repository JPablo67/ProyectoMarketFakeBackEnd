package com.Alejandro.models;

public class ChangeQuantityRequest {

	 private Cart cart;
	    
	 private Integer quantity;

	 
	 
	public ChangeQuantityRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	 
	 
}
