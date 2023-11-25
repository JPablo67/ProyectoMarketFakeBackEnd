package com.Alejandro.controller;

import com.Alejandro.Service.SaleService;
import com.Alejandro.models.Sale;
import com.Alejandro.models.User;
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
    public ResponseEntity<String> makePurchase(@RequestBody User user,
                                               HttpServletResponse response) {
    	
    	System.out.println(user.getIdUser());
        try {
            Sale sale3 = saleService.makePurchase(
                   user
            );

            if (sale3 != null) {
            	 generatePDF(response, sale3);
              
                return ResponseEntity.ok("Compra realizada con éxito. PDF generado.");
            } else {
                System.out.println("El carrito está vacío. No se puede realizar la compra.");
                return ResponseEntity.status(400).body("El carrito está vacío. No se puede realizar la compra.");
            }
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
