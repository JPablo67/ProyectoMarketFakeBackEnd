package com.Alejandro.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.Alejandro.models.User;


public interface IUserRepository  extends JpaRepository<User, Long>{
	
}
