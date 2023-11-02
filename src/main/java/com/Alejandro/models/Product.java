package com.Alejandro.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name ="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idProduct")
	private long idProduct;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "url_photo")
	private String url_photo;
	
	@Column(name = "quantityToSell")
	private int quantityToSell;
	
	@ManyToOne
	@JoinColumn(name = "idCategoria")
	Category category;
	
	@Column(name = "productName")
	private String productName;
	
	@Column(name = "productDescription")
	private String productDescription;
	
	@ManyToOne // Esta anotación indica una relación ManyToOne
	@JoinColumn(name = "idCart") // Nombre de la columna que almacena la relación en la tabla de Producto
	private Cart cart;
	
	@ManyToOne // Esta anotación indica una relación ManyToOne
	@JoinColumn(name = "idSupplier") // Nombre de la columna que almacena la relación en la tabla de Producto
	private Supplier supplier;
	
	@ManyToOne // Esta anotación indica una relación ManyToOne
	@JoinColumn(name = "idSale") // Nombre de la columna que almacena la relación en la tabla de Producto
	private Sale sale;
	
	@ManyToOne // Esta anotación indica una relación ManyToOne
	@JoinColumn(name = "idInventory") // Nombre de la columna que almacena la relación en la tabla de Producto
	private Inventory inventory;
	
	public Product() {
	
	}

	public long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(long idProduct) {
		this.idProduct = idProduct;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getUrl_photo() {
		return url_photo;
	}

	public void setUrl_photo(String url_photo) {
		this.url_photo = url_photo;
	}

	public int getQuantityToSell() {
		return quantityToSell;
	}

	public void setQuantityToSell(int quantityToSell) {
		this.quantityToSell = quantityToSell;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "Product [idProduct=" + idProduct + ", price=" + price + ", url_photo=" + url_photo + ", quantityToSell="
				+ quantityToSell + ", category=" + category + ", productName=" + productName + ", productDescription="
				+ productDescription + ", cart=" + cart + ", supplier=" + supplier + ", sale=" + sale + ", inventory="
				+ inventory + "]";
	}


	
	
	

	
	



	
	
	
	
	
	
}