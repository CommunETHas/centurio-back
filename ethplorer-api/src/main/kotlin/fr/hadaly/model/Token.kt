package fr.hadaly.model

import fr.hadaly.model.serializer.BigIntegerSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import java.math.BigInteger

@Serializable
data class Token(
    val balance: Double,
    @Serializable(with = BigIntegerSerializer::class)
    val rawBalance: BigInteger,
    val tokenInfo: TokenInfo
)

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
    val owner: String? = null, // Address
    @Serializable(with = BigIntegerSerializer::class)
    val totalSupply: BigInteger
)
