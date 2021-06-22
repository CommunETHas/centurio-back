package fr.hadaly.ethplorer.model


data class WalletInfo(
    val address: String,
    val ethInfo: EthInfo,
    val transactionCount: Int,
    val tokens: List<Token>,
)
