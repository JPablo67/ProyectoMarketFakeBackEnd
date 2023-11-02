package com.Alejandro.models;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name ="sales")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idSale")
	private long idSale;
	
	@Column(name = "price")
	private int price;
	

	@OneToMany(mappedBy="sale")
	private List <Product> soldList;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phoneNumber")
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "idUser") 
	User user;

	public Sale() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sale(long idSale, int price, List<Product> soldList, String address, String phoneNumber,
			User user) {
		super();
		this.idSale = idSale;
		this.price = price;
		this.soldList = soldList;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.user = user;
	}

	public long getIdSale() {
		return idSale;
	}

	public void setIdSale(long idSale) {
		this.idSale = idSale;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Product> getSoldList() {
		return soldList;
	}

	public void setSoldList(List<Product> soldList) {
		this.soldList = soldList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	

	
	
}
