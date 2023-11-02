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

import com.Alejandro.Service.SupplierService;
import com.Alejandro.models.Product;
import com.Alejandro.models.Supplier;


@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "http://localhost:4200")
public class DemoControllerSupplier {

	
	 @Autowired
	 private SupplierService supplierService;

	   @GetMapping
	    public ResponseEntity<List<Supplier>> user() {
	        return ResponseEntity.ok(supplierService.suppliers());
	    }

	 

	    @PostMapping
	    public ResponseEntity<Supplier> create(@RequestBody Supplier supplier) {
	        return ResponseEntity.ok(supplierService.save(supplier));
	    }

	    @DeleteMapping("/{idSupplier}/delete")
	    public ResponseEntity<Void> delete(@PathVariable Long idSupplier) {
	    	supplierService.delete(idSupplier);
	        return ResponseEntity.noContent().build();
	    
	    }
	    

	    @PutMapping("/{idSupplier}/update")
	    public ResponseEntity<Supplier> update(@PathVariable Long idSupplier, @RequestBody Supplier supplier) {
	    	Supplier existingSupplier = supplierService.findByIdSupplier(idSupplier);
	        
	        if (existingSupplier != null) {
	            // Actualiza los datos del usuarioExistente con los datos proporcionados en el cuerpo de la solicitud (usuario)
	        	existingSupplier.setNameSupplier(supplier.getNameSupplier());
	        	existingSupplier.setAddressSupplier(supplier.getAddressSupplier());
	        	existingSupplier.setEmailSupplier(supplier.getEmailSupplier());
	        	existingSupplier.setPhoneNumberSupplier(supplier.getPhoneNumberSupplier());
	        	existingSupplier.setUrlSupplier(supplier.getUrlSupplier());

	        	
	           
	            // Guarda el usuario actualizado en la base de datos
	        	Supplier updateSupplier = supplierService.save(existingSupplier);
	            
	            return ResponseEntity.ok(updateSupplier);
	        } else {
	            // Si no se encuentra el usuario, devuelve una respuesta 404 Not Found
	            return ResponseEntity.notFound().build();
	        }
	    }
	    
	    
	    @GetMapping("/{idSupplier}/products")
	    public ResponseEntity<List<Product>>getSuplierById(@PathVariable Long idSupplier) {
	 
	    	  return ResponseEntity.ok(supplierService.getProductsBySupplier(idSupplier));
	        
	        
	    }
}
