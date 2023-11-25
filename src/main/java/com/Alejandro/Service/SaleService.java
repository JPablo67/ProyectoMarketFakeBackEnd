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
	        try {
	            // Obtener la lista de carritos del usuario con la cédula proporcionada
	            List<Cart> cartItems = cartService.findCartByOwner(user.getIdUser());

	            // Verificar si la lista de carritos no está vacía
	            if (!cartItems.isEmpty()) {
	            	
	            	 int quantityToSell=0;
	                // Crear una nueva venta
	                Sale sale = new Sale();
	                // Obtener el usuario por la cédula
	               
	                sale.setUser(user);

	                // Lista de productos vendidos
	                List<Product> soldProducts = new ArrayList<>();

	                // Calcular el precio total y actualizar la cantidad en el inventario
	                int totalPrice = 0;
	               

	                for (Cart cartItem : cartItems) {
	                    Product product = cartItem.getProduct();

	                    // Verificar si hay suficientes productos en el inventario para vender
	                    if (product.getQuantityToSell() >= cartItem.getTotalQuantity() && product.getQuantityToSell() !=0 ) {
	                        int itemTotalPrice = product.getPrice() * cartItem.getTotalQuantity();
	                        totalPrice += itemTotalPrice;

	                        // Actualizar la cantidad en el inventario
	                        int remainingQuantity = product.getQuantityToSell() - cartItem.getTotalQuantity();
	                        product.setQuantityToSell(remainingQuantity);
	                        productRepository.save(product);
	                        // Establecer la lista de productos vendidos en la venta
	    	                sale.setSoldList(cartItems);
	    	                sale.setAddress(user.getAddress());
	    	                sale.setPhoneNumber(user.getPhoneNumber());
	    	                sale.setPrice(totalPrice);

	    	                // Guardar la venta en la base de datos
	    	                saleRepository.save(sale);
	    	                
	                        soldProducts.add(product);
	                    } else {
	                       System.out.println("No hay productos suficientes");
	                    }
	                }

	            
	              
	                // Eliminar productos del carrito y del inventario si la cantidad llega a cero
	                for (Product soldProduct : soldProducts) {
	                    // Eliminar el producto del carrito
	                   

	                    // Verificar si la cantidad en el inventario llega a cero
	                    if (soldProduct.getQuantityToSell() == 0) {
	                    	 cartRepository.deleteByUserAndProduct(user, soldProduct);
	                        productRepository.delete(soldProduct);
	                    }
	                }
	                
	                return sale;

	            } else {
	                // Manejar la situación en la que el carrito está vacío
	                System.out.println("El carrito está vacío. No se puede realizar la compra.");
	                return null;
	            }
	        } catch (Exception e) {
	            // Agregar registros de depuración
	            System.out.println("Error en makePurchase: " + e.getMessage());
	            e.printStackTrace();

	            // Relanzar la excepción después de imprimir la traza
	            throw e;
	          
	        }
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
