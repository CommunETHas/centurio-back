package fr.hadaly.core.model

import kotlinx.serialization.Serializable

@Serializable
data class SimpleToken(
    val name: String,
    val address: String,
    val symbol: String,
    val owner: String,
    val known: Boolean = false,
    val recommendedCovers: List<Cover> = emptyList()
)
