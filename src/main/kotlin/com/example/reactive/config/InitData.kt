package com.example.reactive.config

import com.example.reactive.model.Category
import com.example.reactive.model.Product
import com.example.reactive.repository.CategoryReactiveRepository
import com.example.reactive.repository.ProductReactiveRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import java.util.stream.Stream

@Configuration
class InitData {

    @Bean
    fun initCategory(repository: CategoryReactiveRepository, client: DatabaseClient): ApplicationRunner {
        return ApplicationRunner {
            client.sql(
                "create table IF NOT EXISTS CATEGORY" +
                        "(category_id SERIAL PRIMARY KEY, category_name varchar (255),category_description varchar (255), completed boolean default false);"
            ).fetch().first().subscribe()
            client.sql("DELETE FROM CATEGORY;").fetch().first().subscribe()
            val stream: Stream<Category> = Stream.of<Category>(
                Category(category_name = "CPU", category_description = "Central Processing Unit "),
                Category(category_name = "Motherboard", category_description = "The main printed circuit board (PCB) found in computers and other expandable systems."),
                Category(category_name = "Graphic Card", category_description = "A video card is an expansion card which generates a feed of output images to a display device (such as a computer monitor)."),
                Category(category_name = "Ram For PC", category_description = "RAM (Random Access Memory) is the hardware in a computing device where the operating system (OS)"),
                Category(category_name = "Power Supply", category_description = "A power supply is an electrical device that supplies electric power to an electrical load. ... All power supplies have a power input connection"))

            // initialize the database
            repository.saveAll(Flux.fromStream(stream)).then().subscribe() // execute
        }
    }

    @Bean
    fun initProduct(repository: ProductReactiveRepository, client: DatabaseClient): ApplicationRunner {
        return ApplicationRunner {
            client.sql(
                "create table IF NOT EXISTS PRODUCT" +
                        "(product_id SERIAL PRIMARY KEY, product_name varchar (255),product_description varchar (255),product_price FLOAT,product_category_id INT, completed boolean default false);"
            ).fetch().first().subscribe()
            client.sql("DELETE FROM PRODUCT;").fetch().first().subscribe()
            val stream: Stream<Product> = Stream.of<Product>(
                Product(product_name = "CPU (ซีพียู) AMD sTRX4 RYZEN THREADRIPPER 3970X", product_description = "AMD Ryzen Threadripper",product_price = 1890.99,product_category_id = 1),
                Product(product_name = "CPU (ซีพียู) AMD sTRX4 RYZEN THREADRIPPER 3960X", product_description = "AMD Ryzen Threadripper",product_price = 1590.99,product_category_id = 1),
                Product(product_name = "CPU (ซีพียู) INTEL 2066 CORE I9-10980XE", product_description = "Intel X Series",product_price = 1990.99,product_category_id = 1),
                Product(product_name = "CPU (ซีพียู) INTEL 2066 CORE I9-10940X", product_description = "Intel X Series",product_price = 1890.99,product_category_id = 1)
            )

            // initialize the database
            repository.saveAll(Flux.fromStream(stream)).then().subscribe() // execute
        }
    }



}