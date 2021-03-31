package com.example.reactive.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("product")
data class Product(
    @Id
    var product_id:Int = 0,
    var product_name:String = "",
    var product_description:String = "",
    var product_price:Double = 0.0,
    var product_category_id:Int = 0

)
