package fr.hadaly.model

import fr.hadaly.model.serializer.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class EthInfo(
    val balance: Double,
    @Serializable(with = BigIntegerSerializer::class)
    val rawBalance: BigInteger
)

