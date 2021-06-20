package fr.hadaly.nexusapi.model

import fr.hadaly.nexusapi.model.serializer.DateAddedSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CoverInfo(
    val name: String,
    val type: CoverType,
    val site: String? = null,
    val symbol: String? = null,
    val underlyingToken: String? = null,
    val supportedChains: List<Chain> = emptyList(),
    @Serializable(with = DateAddedSerializer::class)
    val dateAdded: Date,
    val deprecated: Boolean = false,
    val logo: String? = null,
    val github: String? = null,
    val messari: String? = null,
    val note: String? = null,
    val coveredToken: String? = null
)
