package com.rafaelcam.transactionengine.consumer

import com.movilepay.transactionengine.event.TransactionTpvPerAccount
import com.rafaelcam.transactionengine.producer.TransactionProducer
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TransactionTpvPerAccountConsumer {

    companion object {
        private val log = KotlinLogging.logger { TransactionProducer::class.java }
    }

    @KafkaListener(topics = ["TRANSACTIONS_TPV_PER_ACCOUNT"], groupId = "group_spring_boot_kafka_avro")
    fun consume(record: ConsumerRecord<String, TransactionTpvPerAccount>) {
        val key = record.key()
        val transaction = record.value()

        log.info { "Consumed message from transaction_tpv_per_account topic with key [$key] and value [$transaction]" }
    }
}