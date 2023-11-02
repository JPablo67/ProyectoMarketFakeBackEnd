package com.Alejandro.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Alejandro.utils.PDFExporterClass;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "http://localhost:4200")
public class PDFController {
	
	@GetMapping("/export")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");


		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=factura.pdf";
		response.setHeader(headerKey, headerValue);

		PDFExporterClass exporter = new PDFExporterClass();
		exporter.export(response);

	}

}
