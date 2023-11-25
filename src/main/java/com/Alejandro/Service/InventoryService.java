package com.Alejandro.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.stereotype.Service;

import com.Alejandro.models.Cart;
import com.Alejandro.models.Product;
import com.Alejandro.models.Sale;
import com.Alejandro.repository.IProductRepository;
import com.Alejandro.repository.ISaleRepository;


@Service
public class InventoryService {

	 @Autowired
	 private IProductRepository productRepository;
	 
	 @Autowired
		private ISaleRepository saleRepository;
	 
	 
	 @Autowired
		private ProductService productService;
	 
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
	 
	

	  
	    
	 
	

