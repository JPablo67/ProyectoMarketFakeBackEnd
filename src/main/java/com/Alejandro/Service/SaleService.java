package com.Alejandro.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Alejandro.repository.ICartRepository;
import com.Alejandro.repository.IInventoryRepository;
import com.Alejandro.repository.IProductRepository;
import com.Alejandro.repository.ISaleRepository;
import com.Alejandro.repository.IUserRepository;
import com.Alejandro.models.Cart;
import com.Alejandro.models.Product;
import com.Alejandro.models.Sale;
import com.Alejandro.models.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SaleService {
	
	
	  @Autowired
	    private ISaleRepository saleRepository;

	    @Autowired
	    private IProductRepository productRepository;

	    @Autowired
	    private ICartRepository cartRepository;
	    
	    @Autowired
	    private IUserRepository userRepository;

	    
	    @Autowired
	    private CartService cartService;
    @Transactional
    public Sale makePurchase(User user) {
        // Cargar usuario completo desde la base de datos
        User dbUser = userRepository.findById(user.getIdUser())
            .orElseThrow(() -> new EntityNotFoundException(
                "Usuario no encontrado: " + user.getIdUser()));
        // Obtener los carritos del usuario
        List<Cart> cartItems = cartService.findCartByOwner(dbUser.getIdUser());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío. No se puede realizar la compra.");
        }

        // Crear instancia de Sale y asignar datos del usuario
        Sale sale = new Sale();
        sale.setUser(dbUser);
        sale.setAddress(dbUser.getAddress());
        sale.setPhoneNumber(dbUser.getPhoneNumber());

        // Calcular precio total y actualizar inventario
        int totalPrice = 0;
        for (Cart cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getIdProduct())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Producto no encontrado: " + cartItem.getProduct().getIdProduct()));
            int qty = cartItem.getTotalQuantity();
            if (product.getQuantityToSell() < qty) {
                throw new IllegalStateException(
                    "Inventario insuficiente para producto: " + product.getProductName());
            }
            totalPrice += product.getPrice() * qty;
            int remaining = product.getQuantityToSell() - qty;
            product.setQuantityToSell(remaining);
            productRepository.save(product);
        }
        sale.setPrice(totalPrice);
        sale.setSoldList(cartItems);  // Registrar los items vendidos

        // Guardar la venta en base de datos
        saleRepository.save(sale);

        // Limpiar carrito y productos agotados
        for (Cart cartItem : cartItems) {
            int purchasedQty = cartItem.getTotalQuantity();
            // Subtract purchased quantity from remaining cart
            int remainingCartQty = purchasedQty - purchasedQty; // full purchase
            if (remainingCartQty > 0) {
                cartItem.setTotalQuantity(remainingCartQty);
                cartRepository.save(cartItem);
            } else {
            }
            // If product sold out, delete product
            Product p = cartItem.getProduct();
            if (p.getQuantityToSell() == 0) {
                productRepository.delete(p);
            }
        }

        return sale;
    }


    
    
    public List<Sale> sales() {
        return saleRepository.findAll();
    }
    
    @SuppressWarnings("deprecation")
	public Sale findById (long idSale) {
    	
    	return saleRepository.getById(idSale);
    	
    }
    
    
  
    
    public Sale ownerSale(User user){
    	return saleRepository.findByUser(user);
    }
    
    
 
}
