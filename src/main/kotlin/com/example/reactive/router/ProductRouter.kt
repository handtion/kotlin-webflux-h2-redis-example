package com.example.reactive.router

import com.example.reactive.handler.ProductHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class ProductRouter {

    @Bean
    fun productRouterMapping(handler : ProductHandler) : RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                RequestPredicates.GET("/api/product").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::findAll
            ).andRoute(
                RequestPredicates.GET("/api/product/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::findById
            ).andRoute(
                RequestPredicates.POST("/api/product").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::save
            ).andRoute(
                RequestPredicates.PUT("/api/product/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::update
            ).andRoute(
                RequestPredicates.DELETE("/api/product/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::delete
            );
    }
}