package com.Alejandro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Alejandro.models.Sale;
import com.Alejandro.models.User;



public interface ISaleRepository  extends JpaRepository<Sale, Long>{
	
	Sale findByUser (User user);
	

}
