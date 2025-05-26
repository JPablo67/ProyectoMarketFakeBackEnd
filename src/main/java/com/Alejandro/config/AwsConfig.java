package com.Alejandro.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    /** Endpoint de SQS (LocalStack) */
    @Value("${aws.sqs.endpoint}")
    private String sqsEndpoint;

    /** Región AWS (solo para la configuración del cliente) */
    @Value("${aws.region:us-east-1}")
    private String region;

    /** Nombre de la cola donde pondremos los carritos */
    @Value("${aws.sqs.cart-queue.name:cart-queue}")
    private String queueName;

    @Bean
    public AmazonSQS amazonSQS() {
        // Credenciales dummy para LocalStack
        BasicAWSCredentials creds = new BasicAWSCredentials("test", "test");

        // Construye el cliente apuntando a LocalStack
        AmazonSQS client = AmazonSQSClientBuilder.standard()
            .withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(sqsEndpoint, region))
            .withCredentials(new AWSStaticCredentialsProvider(creds))
            .build();

        // Asegura que la cola exista (la crea si hace falta)
        try {
            client.getQueueUrl(queueName);
        } catch (QueueDoesNotExistException e) {
            client.createQueue(new CreateQueueRequest(queueName));
        }

        return client;
    }
}