package fr.hadaly.ethplorer.model

import fr.hadaly.core.model.Wallet
import fr.hadaly.ethplorer.toSimpleToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfo(
    val address: String,
    @SerialName("ETH") val ethInfo: EthInfo,
    @SerialName("countTxs") val transactionCount: Int,
    val tokens: List<Token> = emptyList(),
    val contractInfo: ContractInfo? = null,
    val tokenInfo: TokenInfo? = null
)

fun AddressInfo.toWallet(): Wallet {
    require(contractInfo == null && tokenInfo == null) { IllegalArgumentException("Address is not a wallet") }
    return Wallet(address, transactionCount, tokens.map { it.tokenInfo.toSimpleToken() })
}
