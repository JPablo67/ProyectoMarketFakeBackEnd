package com.Alejandro.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Alejandro.models.Product;
import com.Alejandro.repository.IProductRepository;


@Service
public class InventoryService {

	 @Autowired
	 private IProductRepository productRepository;
	 
	 
	 public List<Product> products() {
	        return productRepository.findAll();
	    }
	 
	 public Product save(Product product) {
	        return productRepository.save(product);
	    }

	 public void delete(Long id) {
	        productRepository.deleteById(id);
	    }
	 
	 public Product edit(Product product) {
	       Product aux = productRepository.findById(product.getIdProduct()).orElse(null);
	       productRepository.deleteById(aux.getIdProduct());
	       return productRepository.save(product);
	    }
	 
	

	  
	    
	 
	
}
