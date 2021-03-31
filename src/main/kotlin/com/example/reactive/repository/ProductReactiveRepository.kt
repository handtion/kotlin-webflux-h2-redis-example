package com.example.reactive.repository

import com.example.reactive.model.Product
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductReactiveRepository : ReactiveCrudRepository<Product, Int> {
}