package com.Alejandro.retry;

import com.Alejandro.models.Cart;
import com.Alejandro.Service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartRetryScheduler {

    @Autowired
    private AmazonSQS sqs;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartService cartService;

    @Value("${aws.sqs.cart.retry-queue.name:cart-retry-queue}")
    private String retryQueueName;

    @Scheduled(fixedDelayString = "${cart.retry.poll.delay:10000}")
    public void pollRetryQueue() {
        String queueUrl = sqs.getQueueUrl(retryQueueName).getQueueUrl();
        ReceiveMessageRequest req = new ReceiveMessageRequest(queueUrl)
            .withMaxNumberOfMessages(10)
            .withWaitTimeSeconds(5);

        List<Message> messages = sqs.receiveMessage(req).getMessages();
        for (Message msg : messages) {
            try {
                // Deserializa y reprocesa
                Cart cart = objectMapper.readValue(msg.getBody(), Cart.class);
                cartService.save(cart);
                // Borra el mensaje de la cola
                sqs.deleteMessage(queueUrl, msg.getReceiptHandle());
            } catch (Exception ex) {
                // Si sigue fallando, lo dejamos para el siguiente poll
                ex.printStackTrace();
            }
        }
    }
}