package com.Alejandro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Alejandro.models.Category;



public interface ICategoryRepository  extends JpaRepository<Category, Long>{

}
