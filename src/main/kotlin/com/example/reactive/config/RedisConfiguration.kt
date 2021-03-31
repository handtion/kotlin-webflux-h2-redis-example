package com.example.reactive.config

import com.example.reactive.model.Category
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory


@Configuration
class RedisConfiguration (val factory : RedisConnectionFactory){

    @Bean
    fun reactiveRedisTemplate(factory: ReactiveRedisConnectionFactory?): ReactiveRedisTemplate<String, Category>? {
        return ReactiveRedisTemplate(factory!!, RedisSerializationContext.newSerializationContext<String, Category>(StringRedisSerializer()).value(Jackson2JsonRedisSerializer(Category::class.java)).build())
    }

}