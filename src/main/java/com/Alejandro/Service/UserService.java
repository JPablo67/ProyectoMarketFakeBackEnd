package com.Alejandro.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
		    List<User> users = Optional.ofNullable(userRepository.findAll()).orElse(Collections.emptyList());

		    return users.stream()
		            .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
		            .map(User::getUserType)
		            .map(userType -> {
		                if ("Cliente".equals(userType)) return 1;
		                else if ("Empleado".equals(userType)) return 2;
		                else if ("Admin".equals(userType)) return 3;
		                else return 0;
		            })
		            .findFirst()
		            .orElse(0);
		}


	  
	 
	
}
