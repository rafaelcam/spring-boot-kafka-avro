package com.rafaelcam.transactionengine.consumer

import com.movilepay.transactionengine.event.Transaction
import com.rafaelcam.transactionengine.producer.TransactionProducer
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TransactionConsumer {

    companion object {
        private val log = KotlinLogging.logger { TransactionProducer::class.java }
    }

    @KafkaListener(topics = ["\${kafka.transaction.topic.name}"], groupId = "group_spring_boot_kafka_avro")
    fun consume(record: ConsumerRecord<String, Transaction>) {
        val key = record.key()
        val transaction = record.value()

        log.info { "Consumed message from transaction topic with key [$key] and value [$transaction]" }
    }
}