package com.example.reactive.repository

import com.example.reactive.model.Category
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryReactiveRepository : ReactiveCrudRepository<Category, Int> {

}
