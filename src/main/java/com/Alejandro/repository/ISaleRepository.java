package com.Alejandro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Alejandro.models.Sale;



public interface ISaleRepository  extends JpaRepository<Sale, Long>{

}
