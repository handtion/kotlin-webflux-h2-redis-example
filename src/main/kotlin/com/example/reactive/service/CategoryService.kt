package com.example.reactive.service

import com.example.reactive.model.Category
import com.example.reactive.repository.CategoryCustomReactiveRepository
import com.example.reactive.repository.CategoryReactiveRepository
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.deleteAndAwait
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class CategoryService(val repository : CategoryReactiveRepository,
                      val customRepository : CategoryCustomReactiveRepository,
                      val redisOperations: ReactiveRedisOperations<String, Category>) {

    fun findAll() : Flux<Category> {

       return redisOperations.keys("*").flatMap(redisOperations.opsForValue()::get)
           .switchIfEmpty( customRepository.findAll() ).doOnNext {  redisOperations.opsForValue().set("${it.category_id}",it).subscribe() }
    }

    fun findById(id : Int) : Mono<Category> {
        return redisOperations.keys("$id").flatMap ( redisOperations.opsForValue()::get ).toMono()
            .switchIfEmpty( customRepository.findById(id).toMono() ).doOnNext { redisOperations.opsForValue().set("${it.category_id}",it).subscribe() }
    }

    fun save(item : Mono<Category>) : Mono<Category>{
        return item.flatMap { repository.save(it) }.doOnNext { ii -> redisOperations.opsForValue().set("${ii.category_id}",ii).subscribe() }
    }

    fun delete(id : Int) : Mono<Void>{
        redisOperations.opsForValue().delete("$id").subscribe()
        return repository.deleteById(id)
    }

    fun update(id : Int,item : Mono<Category>) : Mono<Category>{
       return item.flatMap { item ->
            val itemMono1: Mono<Category> = repository.findById(id)
                .flatMap {
                    val ite : Mono<Category> = repository.save(Category(it.category_id,item.category_name,item.category_description))
                    redisOperations.opsForValue().set("${it.category_id}",item).subscribe()
                    ite
                }
            itemMono1
        }
    }


}