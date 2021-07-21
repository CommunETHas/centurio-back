package fr.hadaly.core.model

data class Wallet(
    val address: String,
    val transactionCount: Int,
    val tokens: List<SimpleToken>,
)
