package com.example.reactive.service

import com.example.reactive.model.Category
import com.example.reactive.repository.CategoryCustomReactiveRepository
import com.example.reactive.repository.CategoryReactiveRepository
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class CategoryService(val repository : CategoryReactiveRepository,
                      val customRepository : CategoryCustomReactiveRepository,
                      val redisOperations: ReactiveRedisOperations<String, Category>) {



    fun findAll() : Flux<Category> {

        customRepository.findAll()
            .flatMap {
                //it.category_name = it.category_name + "_redis"
                redisOperations.opsForValue().set("${it.category_id}",it)
            }.subscribe()

       return redisOperations.keys("*").flatMap(redisOperations.opsForValue()::get)//customRepository.findAll()
    }

    fun findById(id : Int) : Mono<Category> {
        return redisOperations.keys("$id").flatMap(redisOperations.opsForValue()::get).toMono()//repository.findById(id)
    }

    fun save(item : Mono<Category>) : Mono<Category>{
        return item.flatMap { repository.save(it) }
    }

    fun delete(id : Int) : Mono<Void>{
        return repository.deleteById(id)
    }

    fun update(id : Int,item : Mono<Category>) : Mono<Category>{
       return item.flatMap { item ->
            val itemMono1: Mono<Category> = repository.findById(id)
                .flatMap {  repository.save(Category(it.category_id,item.category_name,item.category_description)) }
            itemMono1
        }
    }


}