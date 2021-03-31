package com.example.reactive.handler

import com.example.reactive.model.Product
import com.example.reactive.repository.ProductReactiveRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class ProductHandler(val repository : ProductReactiveRepository) {

    fun findAll(request: ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll(), Product::class.java)


    fun findById(request: ServerRequest): Mono<ServerResponse> {
        val id : Int = request.pathVariable("id").toInt()
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.findById(id), Product::class.java)
    }


    fun save(request: ServerRequest): Mono<ServerResponse> {
        var item : Mono<Product> = request.bodyToMono(Product::class.java)
        return item.flatMap { ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.save(it),Product::class.java)}
    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        val id : Int = request.pathVariable("id").toInt()
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(repository.deleteById(id), Product::class.java)
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        val id : Int = request.pathVariable("id").toInt()



        val updateItem: Mono<Product> = request.bodyToMono(Product::class.java)
            .flatMap { item ->
                val itemMono1: Mono<Product> = repository.findById(id)
                    .flatMap {  repository.save( Product(it.product_id,item.product_name,item.product_description,item.product_price)) }
                itemMono1
            }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(updateItem, Product::class.java)


    }
}