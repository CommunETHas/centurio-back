package fr.hadaly.ethplorer.model

import kotlinx.serialization.Serializable

@Serializable
data class ContractInfo(
    val creatorAddress: String,
    val transactionHash: String,
    val timestamp: Long
)
