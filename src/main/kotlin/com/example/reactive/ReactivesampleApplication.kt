package com.example.reactive

import com.example.reactive.model.Category
import com.example.reactive.repository.CategoryReactiveRepository
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Flux
import java.util.stream.Stream

@SpringBootApplication
class ReactivesampleApplication

	fun main(args: Array<String>) {
		runApplication<ReactivesampleApplication>(*args)
	}


