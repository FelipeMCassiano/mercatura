package com.felipemcassiano.Mercatura.infra.cache;


import com.felipemcassiano.Mercatura.dtos.ProductResponseDTO;
import com.felipemcassiano.Mercatura.infra.CartProductDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, CartProductDTO> cartProductRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, CartProductDTO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(CartProductDTO.class));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, ProductResponseDTO> productResponseRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ProductResponseDTO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ProductResponseDTO.class));

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}


