package com.rafaelcam.transactionengine.webservice

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class TransactionRouter {

    @Bean
    fun transactionRoute(handler: TransactionHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            "/api/v1/transactions".nest {
                POST("", handler::create)
            }
        }
    }
}