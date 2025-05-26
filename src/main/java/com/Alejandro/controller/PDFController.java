package com.Alejandro.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Alejandro.Service.SaleService;
import com.Alejandro.models.Sale;
import com.Alejandro.models.User;
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

    /**
     * Realiza la compra generando la venta, borra el carrito y exporta el PDF.
     */
    @PostMapping("/export-after-purchase")
    public void purchaseAndExport(@RequestBody User user, HttpServletResponse response) {
        try {
            // 1) Generar la venta y eliminar el carrito en el servicio
            Sale sale = saleService.makePurchase(user);

            // 2) Preparar respuesta PDF
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=factura-" + sale.getIdSale() + ".pdf";
            response.setHeader(headerKey, headerValue);

            // 3) Exportar PDF usando los datos de la venta
            pdfExporter.export(response, sale);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

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
