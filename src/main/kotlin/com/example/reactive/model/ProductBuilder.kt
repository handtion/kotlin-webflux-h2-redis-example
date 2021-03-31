package com.example.reactive.model

class ProductBuilder {

    companion object {
        fun build(id : Int,name : String,description : String,price : Double,category_id : Int) : Product {

               return Product(id,name,description,price,category_id)

        }

    }
}