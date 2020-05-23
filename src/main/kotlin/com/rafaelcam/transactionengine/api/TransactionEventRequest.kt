package com.rafaelcam.transactionengine.api

data class TransactionEventRequest(
    val name: String,
    val payload: String
)