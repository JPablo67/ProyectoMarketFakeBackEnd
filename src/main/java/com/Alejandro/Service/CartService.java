package com.Alejandro.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Alejandro.models.Cart;
import com.Alejandro.models.Product;
import com.Alejandro.models.User;
import com.Alejandro.repository.ICartRepository;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Value;


@Service
public class CartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private AmazonSQS sqs;

    @Autowired
    private RedisTemplate<String, Cart> redisTemplate;

    @Value("${aws.sqs.cart.retry-queue.name:cart-retry-queue}")
    private String retryQueueName;

    @Value("${aws.sqs.error-queue.name:error-queue}")
    private String errorQueueName;

    /**
     * Obtiene todos los carritos pertenecientes a un usuario.
     */
    public List<Cart> findCartByOwner(long userId) {
        User user = new User();
        user.setIdUser(userId);
        return cartRepository.findByUser(user);
    }

    /**
     * Agrega un producto al carrito o actualiza la cantidad si ya existe.
     */
    @CircuitBreaker(name = "cartService", fallbackMethod = "saveFallback")
    public Cart save(Cart cart) {
        try {
            Long userId = cart.getUser().getIdUser();
            Long productId = cart.getProduct().getIdProduct();
            Cart existing = findByUserAndProduct(userId, productId);

            Integer frontQty = cart.getTotalQuantity();
            int qtyToAdd = (frontQty == null || frontQty <= 0) ? 1 : frontQty;

            Cart saved;
            if (existing == null) {
                cart.setTotalQuantity(qtyToAdd);
                saved = cartRepository.save(cart);
            } else {
                existing.setTotalQuantity(existing.getTotalQuantity() + qtyToAdd);
                saved = cartRepository.save(existing);
            }

            // Guardar carrito en Redis
            redisTemplate.opsForList().leftPush("carts", saved);

            return saved;
        } catch (Exception e) {
            // Enviar carrito fallido a SQS
            String queueUrl = sqs.getQueueUrl(retryQueueName).getQueueUrl();
            sqs.sendMessage(new SendMessageRequest(queueUrl, cart.toString()));
            throw e;
        }
    }

    // Fallback for save circuit breaker
    public Cart saveFallback(Cart cart, Throwable t) {
        String queueUrl = sqs.getQueueUrl(errorQueueName).getQueueUrl();
        sqs.sendMessage(new SendMessageRequest(queueUrl,
            "[CircuitBreaker save] Error: " + t.getMessage()));
        return null;
    }

    /**
     * Cambia la cantidad de un ítem en el carrito.
     */
    @CircuitBreaker(name = "cartService", fallbackMethod = "changeFallback")
    public Cart changeQuantity(Cart cart, Integer quantity) {
        try {
            if (cart == null) {
                throw new IllegalArgumentException("Cart not found");
            }
            cart.setTotalQuantity(quantity);
            Cart updated = cartRepository.save(cart);

            // Guardar actualización en Redis
            redisTemplate.opsForList().leftPush("carts", updated);

            return updated;
        } catch (Exception e) {
            // Enviar carrito fallido a SQS
            String queueUrl = sqs.getQueueUrl(retryQueueName).getQueueUrl();
            sqs.sendMessage(new SendMessageRequest(queueUrl, cart.toString()));
            throw e;
        }
    }

    // Fallback for changeQuantity circuit breaker
    public Cart changeFallback(Cart cart, Integer quantity, Throwable t) {
        String queueUrl = sqs.getQueueUrl(errorQueueName).getQueueUrl();
        sqs.sendMessage(new SendMessageRequest(queueUrl,
            "[CircuitBreaker changeQuantity] Error: " + t.getMessage()));
        return cart;
    }

    /**
     * Busca el carrito de un usuario para un producto específico.
     */
    public Cart findByUserAndProduct(Long userId, Long productId) {
        List<Cart> carts = findCartByOwner(userId);
        if (carts != null) {
            for (Cart c : carts) {
                Product p = c.getProduct();
                if (p != null && productId.equals(p.getIdProduct())) {
                    return c;
                }
            }
        }
        return null;
    }


    /**
     * Verifica si un producto ya está en el carrito del usuario.
     */
    public boolean checkProductInCart(long userId, long productId) {
        return findByUserAndProduct(userId, productId) != null;
    }
}
