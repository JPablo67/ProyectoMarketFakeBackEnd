package com.Alejandro.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Alejandro.models.Product;
import com.Alejandro.models.Sale;
import com.Alejandro.models.User;
import com.Alejandro.repository.IProductRepository;



@Service
public class ProductService {
	
	  @Autowired
	  private IProductRepository productoRepository;

	 
	  public Product getById(Long idProduct) {
	        return productoRepository.findById(idProduct).orElse(null);
	    }
	   
	  
	 
	  

	   

}
