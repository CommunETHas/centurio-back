package fr.hadaly.model

import kotlinx.serialization.Serializable

@Serializable
data class ContractInfo(
    val creatorAddress: String,
    val transactionHash: String,
    val timestamp: Long
)
