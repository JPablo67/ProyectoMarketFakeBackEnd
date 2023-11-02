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

import com.Alejandro.Service.UserService;

import com.Alejandro.models.User;





@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class DemoControllerUser {

	
	 @Autowired
	 private UserService userService;

	   @GetMapping
	    public ResponseEntity<List<User>> user() {
	        return ResponseEntity.ok(userService.users());
	    }

	 

	    @PostMapping
	    public ResponseEntity<User> create(@RequestBody User usuario) {
	        return ResponseEntity.ok(userService.save(usuario));
	    }

	    @DeleteMapping("/{idUser}/delete")
	    public ResponseEntity<Void> delete(@PathVariable Long idUser) {
	    	userService.delete(idUser);
	        return ResponseEntity.noContent().build();
	    
	    }
	    

	    @PutMapping("/{idUser}/update")
	    public ResponseEntity<User> update(@PathVariable Long idUser, @RequestBody User user) {
	    	User existingUser = userService.findByIdUSer(idUser);
	        
	        if (existingUser != null) {
	            // Actualiza los datos del usuarioExistente con los datos proporcionados en el cuerpo de la solicitud (usuario)
	        	existingUser.setName(user.getName());
	        	existingUser.setLastname(user.getLastname());
	        	existingUser.setEmail(user.getEmail());
	        	existingUser.setPassword(user.getPassword());
	        	existingUser.setPhoneNumber(user.getPhoneNumber());
	        	existingUser.setUserType(user.getUserType());
	           
	            // Guarda el usuario actualizado en la base de datos
	            User updateUser = userService.save(existingUser);
	            
	            return ResponseEntity.ok(updateUser);
	        } else {
	            // Si no se encuentra el usuario, devuelve una respuesta 404 Not Found
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	    
	  
}
