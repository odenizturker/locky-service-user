package com.odenizturker.locky.user.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class ResourceServerConfig(
    @Value("\${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private val jwkSetUri: String,
) {
    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .csrf { it.disable() }
            .authorizeExchange {
                it // .pathMatchers(HttpMethod.POST, "/users").permitAll()
                    .anyExchange()
                    .permitAll()
            }.oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwkSetUri(jwkSetUri)
                }
            }.build()
}
