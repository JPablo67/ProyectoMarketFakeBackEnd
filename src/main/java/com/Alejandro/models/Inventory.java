package com.Alejandro.models;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name ="inventories")
public class Inventory {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idInventory")
	private long idInventory;
	
	
	@OneToMany(mappedBy="inventory")
	private List <Product> inventoryList;


	public Inventory(long idInventory, List<Product> inventoryList) {
		super();
		this.idInventory = idInventory;
		this.inventoryList = inventoryList;
	}


	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getIdInventory() {
		return idInventory;
	}


	public void setIdInventory(long idInventory) {
		this.idInventory = idInventory;
	}


	public List<Product> getInventoryList() {
		return inventoryList;
	}


	public void setInventoryList(List<Product> inventoryList) {
		this.inventoryList = inventoryList;
	}


	
	
	
	
}
