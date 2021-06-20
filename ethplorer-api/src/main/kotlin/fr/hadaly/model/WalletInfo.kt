package fr.hadaly.model

import kotlinx.serialization.Serializable

@Serializable
data class WalletInfo(
    val transactionCount: Int,
    val tokens: List<Token>
)
