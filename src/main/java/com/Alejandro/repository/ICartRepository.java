package com.Alejandro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Alejandro.models.Cart;
import com.Alejandro.models.Product;
import com.Alejandro.models.User;

import jakarta.transaction.Transactional;


public interface ICartRepository  extends JpaRepository<Cart, Long>{

	List<Cart> findByUser (User user);
	
	
	Cart findByProduct (Product product);
	
	 @Transactional
	    void deleteByUserAndProduct(User user, Product product);
	
	
}
