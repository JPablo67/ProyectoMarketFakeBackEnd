package com.Alejandro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Alejandro.Service.InventoryService;
import com.Alejandro.Service.ProductService;
import com.Alejandro.models.Product;


@RestController
@RequestMapping("/api/inventarios")
@CrossOrigin(origins = "http://localhost:4200")
public class DemoControllerInventory {
	
	 @Autowired
	    private InventoryService inventoryService;
	 
	 @Autowired
	    private ProductService productService;

	    @GetMapping
	    public ResponseEntity<List<Product>> products() {
	        return ResponseEntity.ok(inventoryService.products());
	    }

	    @GetMapping("/{codProducto}")
	    public ResponseEntity<Product> edit(@PathVariable Product product) {
	        Product producto1 = inventoryService.edit(product);
	        if (producto1 != null) {
	            return ResponseEntity.ok(product);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @PostMapping
	    public ResponseEntity<Product> create(@RequestBody Product product){
	    	System.out.println(product.getCategory().getIdCategory());
	        return ResponseEntity.ok(inventoryService.save(product));

	    }

	    @DeleteMapping("/{codProducto}/delete")
	    public ResponseEntity<Void> delete(@PathVariable Long codProducto) {
	    	inventoryService.delete(codProducto);
	        return ResponseEntity.noContent().build();
	    }
	    @PutMapping("/{idProduct}/update")
	    public ResponseEntity<Product> update(@PathVariable Long idProduct, @RequestBody Product product) {
	        Product existingProduct = productService.getById(idProduct);
	        
	        if (existingProduct != null) {
	            // Actualiza los atributos del producto existente con los datos proporcionados en el cuerpo de la solicitud
	            existingProduct.setPrice(product.getPrice());
	            existingProduct.setUrl_photo(product.getUrl_photo());
	            existingProduct.setQuantityToSell(product.getQuantityToSell());
	            existingProduct.setCategory(product.getCategory());
	            existingProduct.setProductName(product.getProductName());
	            existingProduct.setProductDescription(product.getProductDescription());
	            existingProduct.setSupplier(product.getSupplier());
	            
	            // Guarda el producto actualizado en la base de datos
	            Product updatedProduct = inventoryService.save(existingProduct);
	            
	            // Devuelve una respuesta 200 OK con el producto actualizado en el cuerpo de la respuesta
	            return ResponseEntity.ok(updatedProduct);
	        } else {
	            // Si el producto no se encuentra, devuelve una respuesta 404 Not Found
	            return ResponseEntity.notFound().build();
	        }
	    }


}
