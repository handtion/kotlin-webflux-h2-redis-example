package com.example.reactive.handler

import com.example.reactive.model.Category
import com.example.reactive.repository.CategoryCustomReactiveRepository
import com.example.reactive.repository.CategoryReactiveRepository
import com.example.reactive.service.CategoryService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class CategoryHandler (val categoryService: CategoryService) {

    fun findAll(request: ServerRequest): Mono<ServerResponse> {

       return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body( categoryService.findAll() , Category::class.java)
    }

    fun findById(request: ServerRequest): Mono<ServerResponse> {

        val id : Int = request.pathVariable("id").toInt()
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body( categoryService.findById(id) ,Category::class.java)
    }


    fun save(request: ServerRequest): Mono<ServerResponse> {
        var item : Mono<Category> = request.bodyToMono(Category::class.java)
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(categoryService.save(item),Category::class.java)

    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        val id : Int = request.pathVariable("id").toInt()
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(categoryService.delete(id),Category::class.java)
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        val id : Int = request.pathVariable("id").toInt()
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(categoryService.update(id,request.bodyToMono(Category::class.java)),Category::class.java)


    }


}