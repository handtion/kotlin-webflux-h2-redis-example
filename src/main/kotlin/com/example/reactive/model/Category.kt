package com.example.reactive.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("category")
data class Category(
    @Id
    @Column("category_id")
    val category_id:Int = 0,
    @Column("category_name")
    var category_name:String = "",
    @Column("category_description")
    var category_description:String = ""
){
    @Transient
    var category_product_list:List<Product> = listOf()
}
