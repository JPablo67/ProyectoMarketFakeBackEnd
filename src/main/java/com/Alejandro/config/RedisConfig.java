package com.Alejandro.config;

import com.Alejandro.models.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Cart> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Cart> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Use String for keys
        template.setKeySerializer(new StringRedisSerializer());
        // Serialize Cart objects as JSON
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}