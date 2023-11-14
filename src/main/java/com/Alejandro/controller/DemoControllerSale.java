package com.Alejandro.controller;

import com.Alejandro.Service.SaleService;
import com.Alejandro.models.Sale;

import com.Alejandro.utils.PDFExporterClass;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:4200")
public class DemoControllerSale {

    @Autowired
    private SaleService saleService;

    @Autowired
    private PDFExporterClass pdfExporterClass;
    
    @PostMapping("/make-purchase")
    public ResponseEntity<String> makePurchase(@RequestBody Sale sale,
                                               HttpServletResponse response,
                                               @RequestParam(required = false) Integer totalQuantity) {
        // Después de realizar la compra, generar el PDF
       
    	 
       
    	
        try {
            if (totalQuantity == null) {
                // Manejar la situación en la que totalQuantity es null
                throw new IllegalArgumentException("La cantidad total no puede ser nula");
            }

            // Resto del código
         Sale  sale3= saleService.makePurchase(
                sale.getUser().getIdUser(),
                sale.getAddress(),
                sale.getPhoneNumber(),
                totalQuantity.intValue()
            );
            

            Sale sale2 =saleService.findById(sale3.getIdSale());
      	
            generatePDF(response, sale2);

            return ResponseEntity.ok("Compra realizada con éxito. PDF generado.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al realizar la compra: " + e.getMessage());
        }
    }


    private void generatePDF(HttpServletResponse response, Sale sale) {
        try {
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=factura.pdf";
            response.setHeader(headerKey, headerValue);

            pdfExporterClass.export(response, sale);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
