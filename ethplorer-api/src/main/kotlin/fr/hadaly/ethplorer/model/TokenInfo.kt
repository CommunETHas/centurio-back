package fr.hadaly.ethplorer.model

import fr.hadaly.ethplorer.model.serializer.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class TokenInfo(
    val address: String, // Address
    val coingecko: String? = null,
    val decimals: Int,
    val ethTransfersCount: Int? = null,
    val holdersCount: Int,
    val image: String? = null,
    val issuancesCount: Int,
    val lastUpdated: Long,
    val name: String,
    val symbol: String,
    val owner: String? = null, // Address
    @Serializable(with = BigIntegerSerializer::class)
    val totalSupply: BigInteger
)
