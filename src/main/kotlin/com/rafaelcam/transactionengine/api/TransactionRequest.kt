package com.rafaelcam.transactionengine.api

import java.util.*

data class TransactionRequest(
    val accountReference: UUID,
    val type: String,
    val amount: Long,
    val currency: String,
    val externalReference: String,
    val events: List<TransactionEventRequest>
)