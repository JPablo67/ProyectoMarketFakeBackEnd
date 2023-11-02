package com.Alejandro.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity(name ="categories")
public class Category {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCategory")
	private Long idCategory;
	
	
	
	@Column(name = "categoryName")
	private String categoryName;
	
	


	public Category() {

	}




	public Long getIdCategory() {
		return idCategory;
	}




	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
	}




	public String getCategoryName() {
		return categoryName;
	}




	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}




	




	

	
	

	
	
	
}
