package com.example.reactive.repository

import com.example.reactive.model.*
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.stream.Collectors

@Repository
class CategoryCustomReactiveRepository(val client: DatabaseClient) {


    fun findAll(): Flux<Category>{
        return client.sql(""" 
             SELECT C.*,P.* FROM CATEGORY C LEFT JOIN PRODUCT P ON C.CATEGORY_ID = P.PRODUCT_CATEGORY_ID
             ORDER BY C.CATEGORY_ID,P.PRODUCT_ID
        """.trimIndent()).fetch().all()
            .bufferUntilChanged { result -> result["category_id"] }
            .map {
                    list -> CategoryBuilder.build(
                                list[0]["category_id"] as Int,
                                list[0]["category_name"] as String,
                                list[0]["category_description"] as String,
                                list.stream().filter { it["product_id"]!=null }.map {
                                    item -> ProductBuilder.build(
                                                (item["product_id"] ?: 0) as Int,
                                                (item["product_name"] ?: "") as String,
                                                (item["product_description"] ?: "") as String,
                                                (item["product_price"] ?: 0.0) as Double,
                                                (item["product_category_id"] ?: 0) as Int
                                            )
                                }.collect(Collectors.toList())
                            )
            }

    }

    fun findById(id: Int) : Flux<Category> {
        return client.sql(""" 
             SELECT C.*,P.* FROM CATEGORY C LEFT JOIN PRODUCT P ON C.CATEGORY_ID = P.PRODUCT_CATEGORY_ID
             WHERE C.CATEGORY_ID = '$id'
             ORDER BY C.CATEGORY_ID,P.PRODUCT_ID
        """.trimIndent()).fetch().all()
            .bufferUntilChanged { result -> result["category_id"] }
            .map {
                    list -> CategoryBuilder.build(
                list[0]["category_id"] as Int,
                list[0]["category_name"] as String,
                list[0]["category_description"] as String,
                list.stream().filter { it["product_id"]!=null }.map {
                        item -> ProductBuilder.build(
                    (item["product_id"] ?: 0) as Int,
                    (item["product_name"] ?: "") as String,
                    (item["product_description"] ?: "") as String,
                    (item["product_price"] ?: 0.0) as Double,
                    (item["product_category_id"] ?: 0) as Int
                )
                }.collect(Collectors.toList())
            )
            }

    }

}

