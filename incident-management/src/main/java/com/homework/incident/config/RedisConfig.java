package com.homework.incident.config;

import com.homework.incident.entity.Incident;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Incident> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Incident> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 配置序列化方式
        template.setKeySerializer(new StringRedisSerializer());  // 用于序列化 Redis 的键
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());  // 用于序列化 Redis 的值

        return template;
    }
}

