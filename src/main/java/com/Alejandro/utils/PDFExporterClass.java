package com.Alejandro.utils;

import com.Alejandro.Service.CartService;
import com.Alejandro.Service.SaleService;
import com.Alejandro.models.Cart;
import com.Alejandro.models.Sale;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
public class PDFExporterClass {

	@Autowired
	CartService cartService;
	
	@Autowired
	SaleService saleService;
	
    public void export(HttpServletResponse response, Sale sale) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Configurar fuente y colores
        Font headingFont = new Font(BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 18, Font.BOLD, Color.BLUE);
        Font subHeadingFont = new Font(BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), 14, Font.BOLD, Color.DARK_GRAY);
        Font normalFont = new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), 12, Font.NORMAL, Color.BLACK);

        // Agregar detalles de la venta al PDF
        addSaleDetails(document, sale, headingFont, subHeadingFont, normalFont);

        // Agregar detalles de los productos vendidos al PDF
        addSoldProducts(document, sale.getUser().getIdUser(), subHeadingFont, normalFont);

        document.close();
    }

    private void addSaleDetails(Document document, Sale sale, Font headingFont, Font subHeadingFont, Font normalFont) throws DocumentException {
    	
    
    	
        document.add(new Paragraph("Detalles de la Venta", headingFont));
        document.add(new Paragraph("Dirección: " + sale.getAddress(), subHeadingFont));
        document.add(new Paragraph("Teléfono: " + sale.getPhoneNumber(), subHeadingFont));
        document.add(new Paragraph("Precio total: $" + sale.getPrice(), subHeadingFont));
        // Agregar más detalles según sea necesario
        document.add(Chunk.NEWLINE); // Separador entre secciones
    }

    private void addSoldProducts(Document document, long idUser, Font subHeadingFont, Font normalFont) throws DocumentException {
        document.add(new Paragraph("Productos Vendidos", subHeadingFont));
        
        List<Cart>carts = cartService.findCartByOwner(idUser);
             

        for (Cart cart : carts) {
            document.add(new Paragraph("Nombre: " + cart.getProduct().getProductName(), normalFont));
            document.add(new Paragraph("Precio: $" + cart.getProduct().getPrice(), normalFont));
            // Agregar más detalles según sea necesario
            document.add(Chunk.NEWLINE); // Separador entre productos
        }
    }
}
