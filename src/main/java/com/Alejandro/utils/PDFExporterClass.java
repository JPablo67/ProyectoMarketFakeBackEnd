package com.Alejandro.utils;

import com.Alejandro.Service.CartService;
import com.Alejandro.Service.SaleService;
import com.Alejandro.Service.UserService;
import com.Alejandro.models.Cart;
import com.Alejandro.models.Sale;
import com.Alejandro.models.User;
import com.Alejandro.repository.ICartRepository;
import com.Alejandro.repository.IProductRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
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
    private CartService cartService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ICartRepository cartRepository;
    
    @Autowired
    private IProductRepository productRepository;

    public void export(HttpServletResponse response, Sale sale) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Configurar fuente y colores
        Font headingFont = getFont(Font.BOLD, 18, Color.BLUE);
        Font subHeadingFont = getFont(Font.BOLD, 14, Color.DARK_GRAY);
        Font normalFont = getFont(Font.NORMAL, 12, Color.BLACK);

        // Agregar detalles de la venta al PDF
        addSaleDetails(document, sale, headingFont, subHeadingFont, normalFont);

        // Agregar detalles de los productos vendidos al PDF
        addSoldProducts(document, sale.getUser().getIdUser(), subHeadingFont, normalFont);

        document.close();
    }

    private Font getFont(int style, float size, Color color) throws DocumentException, IOException {
        return new Font(BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.EMBEDDED), size, style, color);
    }

    private void addSaleDetails(Document document, Sale sale, Font headingFont, Font subHeadingFont, Font normalFont) throws DocumentException {
        User user = userService.findByIdUSer(sale.getUser().getIdUser());
        addParagraph(document, "Detalles de la Venta", headingFont);
        addParagraph(document, "Nombre: " + user.getName(), subHeadingFont);
        addParagraph(document, "Apellido: " + user.getLastname(), subHeadingFont);
        addParagraph(document, "Cedula: " + sale.getUser().getIdUser(), subHeadingFont);
        addParagraph(document, "Dirección: " + user.getAddress(), subHeadingFont);
        addParagraph(document, "Teléfono: " + user.getPhoneNumber(), subHeadingFont);
        addParagraph(document, "Precio total: $" + sale.getPrice(), subHeadingFont);
        document.add(Chunk.NEWLINE); // Separador entre secciones
    }

    private void addSoldProducts(Document document, long idUser, Font subHeadingFont, Font normalFont) throws DocumentException {
        addParagraph(document, "Productos Vendidos", subHeadingFont);

        List<Cart> carts = cartService.findCartByOwner(idUser);

        // Crear una tabla con 3 columnas para Nombre, Precio y Cantidad
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);

        // Encabezados de la tabla
        addCell(table, "Nombre", subHeadingFont);
        addCell(table, "Precio", subHeadingFont);
        addCell(table, "Cantidad", subHeadingFont);
        addCell(table, "iva", subHeadingFont);
        addCell(table, "total de envio", subHeadingFont);


        for (Cart cart : carts) {
            // Filas de la tabla con los detalles de cada producto
            addCell(table, cart.getProduct().getProductName(), normalFont);
            addCell(table, "$" + cart.getProduct().getPrice(), normalFont);
            addCell(table, String.valueOf(cart.getTotalQuantity()), normalFont);
            addCell(table, String.valueOf("iva = 19%"), normalFont);
            addCell(table, String.valueOf(cart.getProduct().getShippingValue()), normalFont);
        }

        // Agregar la tabla al documento
        document.add(table);

        document.add(Chunk.NEWLINE); // Separador después de la tabla

        // Eliminar los productos del carrito después de generar el PDF
        for (Cart cart : carts) {
            cartRepository.deleteById(cart.getIdCart());
           
                   }
    }

    private void addParagraph(Document document, String content, Font font) throws DocumentException {
        document.add(new Paragraph(content, font));
    }

    private void addCell(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}
