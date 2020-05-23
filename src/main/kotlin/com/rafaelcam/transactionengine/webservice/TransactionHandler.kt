package com.rafaelcam.transactionengine.webservice

import com.movilepay.transactionengine.event.Transaction
import com.movilepay.transactionengine.event.TransactionCurrency
import com.movilepay.transactionengine.event.TransactionEvent
import com.movilepay.transactionengine.event.TransactionType
import com.rafaelcam.transactionengine.api.TransactionRequest
import com.rafaelcam.transactionengine.producer.TransactionProducer
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.time.Instant
import java.util.*

@Component
class TransactionHandler(private val producer: TransactionProducer) {

    companion object {
        private val log = KotlinLogging.logger { TransactionHandler::class.java }
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        try {
            val body = request.awaitBody<TransactionRequest>()

            val transactionId = UUID.randomUUID().toString()
            val transaction = Transaction.newBuilder()
                .setId(transactionId)
                .setAccountReference(body.accountReference.toString())
                .setType(TransactionType.valueOf(body.type))
                .setAmount(body.amount)
                .setCurrency(TransactionCurrency.valueOf(body.currency))
                .setExternalReference(body.externalReference)
                .setCreatedAt(Instant.now().toEpochMilli())
                .setEvents(
                    body.events.map {
                        TransactionEvent.newBuilder()
                            .setId(UUID.randomUUID().toString())
                            .setName(it.name)
                            .setTransactionId(transactionId)
                            .setPayload(it.payload)
                            .setCreatedAt(Instant.now().toEpochMilli())
                            .build()
                    }
                )
                .build()

            producer.sendMessage(transaction)

            return ok().bodyValueAndAwait(body)
        } catch (ex: Exception) {
            log.error(ex) { "An error ocurred" }
            return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValueAndAwait(ex)
        }
    }
}