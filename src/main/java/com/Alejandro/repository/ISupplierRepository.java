package com.Alejandro.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.Alejandro.models.Supplier;

public interface ISupplierRepository  extends JpaRepository<Supplier, Long>{

}
