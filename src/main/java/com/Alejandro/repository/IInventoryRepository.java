package com.Alejandro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Alejandro.models.Inventory;


public interface IInventoryRepository  extends JpaRepository<Inventory, Long>{

}
