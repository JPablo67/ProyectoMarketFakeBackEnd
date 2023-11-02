package com.Alejandro.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Alejandro.models.Category;
import com.Alejandro.models.Product;
import com.Alejandro.models.User;
import com.Alejandro.repository.ICategoryRepository;
import com.Alejandro.repository.IProductRepository;


@Service
public class CategoryService {

	@Autowired
	   private ICategoryRepository categoryRepository;
	
	@Autowired
	   private IProductRepository productRepository;
	
	 
	 
	 public List<Category> categories() {
	        return categoryRepository.findAll();
	    }
	 
	 public Category save(Category category) {
	        return categoryRepository.save(category);
	    }
	 
	 public void delete(Long idCategory) {
	         categoryRepository.deleteById(idCategory);
	    }
	 
	 public Category getCategoryById(Long idCategory) {
	        return categoryRepository.findById(idCategory).orElse(null);
	    }
	 
	 public List<Product> getProductsByCategory(long idCategory){
		 Category category = new Category();
		 category.setIdCategory(idCategory);
		 List<Product> listaProductosCategoria =  productRepository.findByCategory(category);
		 return listaProductosCategoria;
	    
	 }


	
}
