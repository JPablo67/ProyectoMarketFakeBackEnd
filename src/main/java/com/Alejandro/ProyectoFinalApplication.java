package com.Alejandro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan("com.Alejandro.models")
public class ProyectoFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}
	

}
