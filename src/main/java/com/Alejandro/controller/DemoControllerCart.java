package com.Alejandro.controller;

import com.Alejandro.Service.CartService;
import com.Alejandro.models.Cart;
import com.Alejandro.models.ChangeQuantityRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {"http://localhost:8081", "https://localhost:8443"})
public class DemoControllerCart {

    @Value("${aws.sqs.cart-queue.name:cart-queue}")
    private String queueName;

    @Autowired
    private CartService cartService;

    @Autowired
    private AmazonSQS sqs;

    @Autowired
    private RedisTemplate<String, Cart> redisTemplate;

    @PostMapping
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        Cart saved = cartService.save(cart);

        // Enviar ID de carrito a SQS
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();
        sqs.sendMessage(new SendMessageRequest(queueUrl, String.valueOf(saved.getIdCart())));

        // Guardar carrito en Redis
        String redisKey = "cart:user:" + saved.getUser().getIdUser();
        redisTemplate.opsForValue().set(redisKey, saved);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{idUser}/carts")
    public ResponseEntity<List<Cart>> getUserById(@PathVariable Long idUser) {
        List<Cart> carts = cartService.findCartByOwner(idUser);
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/checkProductInCart/{idUser}/{idProduct}")
    public ResponseEntity<Map<String, Boolean>> checkProductInCart(
            @PathVariable long idUser,
            @PathVariable long idProduct) {

        boolean isProductInCart = cartService.checkProductInCart(idUser, idProduct);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isProductInCart", isProductInCart);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/changeQuantity")
    public ResponseEntity<Cart> changeQuantity(@RequestBody ChangeQuantityRequest request) {
        Cart cart = cartService.findByUserAndProduct(request.getUserId(), request.getProductId());
        Cart updated = cartService.changeQuantity(cart, request.getQuantity());

        // Actualizar carrito en Redis
        String redisKey = "cart:user:" + request.getUserId();
        redisTemplate.opsForValue().set(redisKey, updated);

        // Notificar cambio a SQS
        String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();
        sqs.sendMessage(new SendMessageRequest(queueUrl,
                "quantityChanged:" + updated.getIdCart()));

        return ResponseEntity.ok(updated);
    }
}
