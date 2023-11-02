package com.Alejandro.models;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name ="carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCart")
	private long idCart;
	



	public Cart(long idCart, List <Product> cartList ) {
		super();
		this.idCart = idCart;
		
	}


	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getIdCart() {
		return idCart;
	}


	public void setIdCart(long idCart) {
		this.idCart = idCart;
	}


	

	
	
	
	
	
}
