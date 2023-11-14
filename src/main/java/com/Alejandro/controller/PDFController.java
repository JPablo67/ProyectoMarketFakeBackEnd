package com.Alejandro.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Alejandro.Service.SaleService;
import com.Alejandro.models.Sale;
import com.Alejandro.repository.ISaleRepository;
import com.Alejandro.utils.PDFExporterClass;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "http://localhost:4200")
public class PDFController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private PDFExporterClass pdfExporter;

    @GetMapping("/export/{saleId}")
    public void exportToPDF(@PathVariable Long saleId, HttpServletResponse response) {
        try {
            Sale sale = saleService.findById(saleId);

            response.setContentType("application/pdf");
            pdfExporter.export(response, sale);
            
            if (sale != null) {
             

                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=factura.pdf";
                response.setHeader(headerKey, headerValue);

               
            } else {
                // Manejar el caso en que no se encuentre la venta
                response.getWriter().write("Venta no encontrada");
            }
        } catch (Exception e) {
            // Manejar las excepciones
            e.printStackTrace();
        }
    }
}






