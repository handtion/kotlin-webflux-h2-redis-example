package com.example.reactive.router

import com.example.reactive.handler.CategoryHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CategoryRouter {

    @Bean
    fun categoryRouterMapping(handler : CategoryHandler) : RouterFunction<ServerResponse>{
        return RouterFunctions
                .route(
                    GET("/api/category").and(accept(MediaType.APPLICATION_JSON)),handler::findAll
                ).andRoute(
                    GET("/api/category/{id}").and(accept(MediaType.APPLICATION_JSON)),handler::findById
                ).andRoute(
                    POST("/api/category").and(accept(MediaType.APPLICATION_JSON)),handler::save
                ).andRoute(
                    PUT("/api/category/{id}").and(accept(MediaType.APPLICATION_JSON)),handler::update
                ).andRoute(
                    DELETE("/api/category/{id}").and(accept(MediaType.APPLICATION_JSON)),handler::delete
                );
    }
}