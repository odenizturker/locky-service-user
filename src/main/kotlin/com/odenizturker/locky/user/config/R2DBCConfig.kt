package com.odenizturker.locky.user.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import io.r2dbc.spi.Option
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager

@Configuration
@EnableR2dbcRepositories
class R2DBCConfig(
    private val prop: R2dbcProperties,
    private val objectMapper: ObjectMapper,
) : AbstractR2dbcConfiguration() {
    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val options = ConnectionFactoryOptions.parse(prop.url)
        val host = options.getValue(Option.valueOf<String>("host")) as String
        val port = options.getValue(Option.valueOf<String>("port")) as Int
        val database = options.getValue(Option.valueOf<String>("database")) as String

        val builder =
            PostgresqlConnectionConfiguration
                .builder()
                .database(database)
                .username(prop.username)
                .password(prop.password)
                .host(host)
                .port(port)
                .preparedStatementCacheQueries(0)

        val postgresConnection = PostgresqlConnectionFactory(builder.build())

        return if (prop.pool.isEnabled) {
            val poolConfig =
                ConnectionPoolConfiguration
                    .builder()
                    .connectionFactory(postgresConnection)
                    .initialSize(prop.pool.initialSize)
                    .maxSize(prop.pool.maxSize)
                    .maxAcquireTime(prop.pool.maxAcquireTime)
                    .maxIdleTime(prop.pool.maxIdleTime)
                    .maxLifeTime(prop.pool.maxLifeTime)
                    .maxCreateConnectionTime(prop.pool.maxCreateConnectionTime)
                    .validationDepth(prop.pool.validationDepth)
                    .build()
            ConnectionPool(poolConfig)
        } else {
            postgresConnection
        }
    }

    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory?): ReactiveTransactionManager = R2dbcTransactionManager(connectionFactory!!)
}
