package fr.hadaly.model

import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    val name: String,
    val type: CoverType,
    val site: String? = null,
    val symbol: String? = null,
    val underlyingToken: String? = null,
    val supportedChains: List<Chain> = emptyList(),
    val dateAdded: String,
    val deprecated: String? = null,
    val logo: String? = null,
    val github: String? = null,
    val messari: String? = null,
    val note: String? = null,
    val coveredToken: String? = null
)
