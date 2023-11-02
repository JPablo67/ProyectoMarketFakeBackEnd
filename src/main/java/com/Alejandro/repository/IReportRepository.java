package com.Alejandro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Alejandro.models.Report;


public interface IReportRepository  extends JpaRepository<Report, Long>{

}
