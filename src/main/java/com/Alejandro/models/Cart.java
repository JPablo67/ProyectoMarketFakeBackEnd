package com.Alejandro.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity(name ="carts")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCart")
	private long idCart;

	
	@ManyToOne // Esta anotación indica una relación ManyToOne
	@JoinColumn(name = "idUser")
	private User user;
	
	@ManyToOne // Esta anotación indica una relación ManyToOne
	@JoinColumn(name = "idProduct")
	private Product product;


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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
}
