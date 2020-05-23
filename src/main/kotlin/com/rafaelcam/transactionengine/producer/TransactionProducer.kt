package com.rafaelcam.transactionengine.producer

import com.movilepay.transactionengine.event.Transaction
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Component
class TransactionProducer(
    @Value("\${kafka.transaction.topic.name}") private val topic: String,
    private val kafkaTemplate: KafkaTemplate<String, Transaction>
) {

    companion object {
        private val log = KotlinLogging.logger { TransactionProducer::class.java }
    }

    suspend fun sendMessage(transaction: Transaction) {
        kafkaTemplate.send(topic, transaction.getId(), transaction)
        log.info { "Produce transaction event with key [${transaction.getId()}] and value [$transaction]" }
    }
}