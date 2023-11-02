package com.Alejandro.controller;


import com.Alejandro.Service.ProductService;
import com.Alejandro.models.Product;
import com.Alejandro.repository.ICartRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import com.Alejandro.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class DemoControllerCart {

	 @Autowired
	 private ProductService productService;
	
	 private List<Product> cart = new ArrayList<>(); // Variable local para el carrito
	 
	    @GetMapping("/showCart")
	    public List<Product> verCarrito() {
	        return cart;
	    }
	    
	    @GetMapping("/buscar/{idProduct}")
	    public Product buscar(@PathVariable Long idProduct) {
	    	for(int i=0; i<cart.size(); i++) {
	    		if(cart.get(i).getIdProduct()==idProduct) {
	    			return cart.get(i);
	    		}
	    	}
			return null;
	    }

	    @PostMapping("/agregar-producto/{idProduct}")
	    public void agregarProductoAlCarrito(@PathVariable Long idProduct) {
	        // Recupera el producto por su ID (suponiendo que tienes un método en productService para hacer esto)
	        
	    	Product product = productService.getById(idProduct);
	    	System.out.println(product.toString());
	        if (product != null) {
	            cart.add(product); // Agrega el producto al carrito
	        }
	    }
	    @DeleteMapping("/borrar-Carrito/{idProduct}")
	    public void borraCarrito(@PathVariable Long idProduct) {
	     int aux = 0;
	    	for(int i=0; i<cart.size(); i++) {
	    		if(cart.get(i).getIdProduct()==idProduct) {
	    			aux=i;
	    		}
	    	}
	        
	        if ( aux >=0) {
	
	            cart.remove(aux); // Elimina el producto del carrito si está presente
	            System.out.println("Producto eliminado del carrito: ");
	        } else {
	            System.out.println("Producto no encontrado en el carrito.");
	        }
	    	
	    }
	    
	    @PutMapping("/actualizar-Carrito/{idProduct}")
	    public void updateCarrito(@PathVariable Long idProduct, @RequestBody Product updatedProduct) {
	        for (int i = 0; i < cart.size(); i++) {
	            if (cart.get(i).getIdProduct() == idProduct) {
	                // Encuentra el producto en el carrito por su ID
	                Product productInCart = cart.get(i);

	                // Actualiza los atributos del producto en el carrito con los valores proporcionados
	                productInCart.setPrice(updatedProduct.getPrice());
	                productInCart.setUrl_photo(updatedProduct.getUrl_photo());
	                productInCart.setQuantityToSell(updatedProduct.getQuantityToSell());
	                productInCart.setCategory(updatedProduct.getCategory());
	                productInCart.setProductName(updatedProduct.getProductName());
	                productInCart.setProductDescription(updatedProduct.getProductDescription());
	                productInCart.setSupplier(updatedProduct.getSupplier());

	                System.out.println("Producto actualizado del carrito: " + productInCart.toString());
	                return;
	            }
	        }

	        System.out.println("Producto no encontrado en el carrito.");
	    }

	    

   

}

