package com.rafaelcam.transactionengine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootKafkaAvroApplication

fun main(args: Array<String>) {
	runApplication<SpringBootKafkaAvroApplication>(*args)
}
