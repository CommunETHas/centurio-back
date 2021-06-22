package fr.hadaly.ethplorer.model

import fr.hadaly.ethplorer.model.serializer.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class Token(
    val balance: Double,
    @Serializable(with = BigIntegerSerializer::class)
    val rawBalance: BigInteger,
    val tokenInfo: TokenInfo
)
