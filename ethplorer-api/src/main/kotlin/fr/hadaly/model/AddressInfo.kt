package fr.hadaly.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfo(
    val address: String,
    @SerialName("ETH") val ethInfo: EthInfo,
    @SerialName("countTxs") val transactionCount: Int,
    val tokens: List<Token>,
    val contractInfo: ContractInfo? = null,
    val tokenInfo: TokenInfo? = null
)

fun AddressInfo.toWalletInfo(): WalletInfo {
    require(contractInfo == null && tokenInfo == null) { IllegalArgumentException("Address is not a wallet") }
    return WalletInfo(address, ethInfo, transactionCount, tokens)
}
