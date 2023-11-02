package com.Alejandro.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Alejandro.Service.ProductService;
import com.Alejandro.config.CloudinaryConfig;
import com.Alejandro.models.Image;
import com.Alejandro.models.Product;

import com.cloudinary.utils.ObjectUtils;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:4200")
public class DemoControllerProduct {

	@Autowired
	private ProductService productService;

	@Autowired
	private CloudinaryConfig cloudinary;

	@GetMapping("/{idProduct}/product")
	public ResponseEntity<Product> obtenerPorId(@PathVariable Long idProduct) {
		Product product = productService.getById(idProduct);
		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/image")
	public ResponseEntity<Image> addProducto(@RequestParam("file") MultipartFile file) throws IOException {
		Map uploadResult = cloudinary.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));

		try {
			System.out.println(uploadResult.get("url").toString());
			String url = uploadResult.get("url").toString();
			Image image = new Image(url);
			return ResponseEntity.ok(image);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}
