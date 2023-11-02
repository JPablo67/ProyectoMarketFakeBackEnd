package com.Alejandro.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Alejandro.models.User;
import com.Alejandro.repository.IUserRepository;




@Service
public class UserService {

    @Autowired
	private IUserRepository userRepository;
	 
    public List<User> users() {
        return userRepository.findAll();
    }
	 
	 public User save(User usuario) {
	        return userRepository.save(usuario);
	    }

	 public void delete(Long idUser) {
	        userRepository.deleteById(idUser);
	    }

	 
	 
	 public User findByIdUSer(Long idUser) {
	        return userRepository.findById(idUser).orElse(null);
	    }
	 
	 public int findLogin(String email, String password) {
		   
		    List<User> users = userRepository.findAll();

		    for (User user : users) {
		        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
		            String userType = user.getUserType();

		            // Verifica el tipo de usuario y devuelve el resultado.
		            if ("Cliente".equals(userType)) {
		                return 1;
		            } else if ("Empleado".equals(userType)) {
		                return 2;
		            } else if ("Admin".equals(userType)) {
		                return 3;
		            }
		        }
		    }

		  
		    return 0;
		}
	 

	  
	 
	
}
