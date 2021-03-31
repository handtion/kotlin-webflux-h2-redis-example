package com.example.reactive.model

class CategoryBuilder {

    companion object {
        fun build(id: Int,name: String,description: String,list:List<Product>) : Category {
            val  item : Category = Category(id,name,description)
            item.category_product_list = list
            return item
        }
    }


}