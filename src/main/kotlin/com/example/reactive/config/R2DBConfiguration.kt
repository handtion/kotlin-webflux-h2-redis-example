package com.example.reactive.config

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories


@Configuration
@EnableR2dbcRepositories
class R2DBConfiguration : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory {
        return H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                .url("mem:testdb;DB_CLOSE_DELAY=-1;")
                .username("sa")
                .build()
        )
    }


}



