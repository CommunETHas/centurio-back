package fr.hadaly.core.model

import fr.hadaly.core.ResourceUrlSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Required

@Serializable
data class SimpleToken(
    val name: String,
    val address: String,
    val symbol: String,
    val owner: String? = null,
    val known: Boolean = false,
    val recommendedCovers: List<Cover> = emptyList(),
    @Required
    @Serializable(with = ResourceUrlSerializer::class)
    val logoUrl: ResourceUrl = ResourceUrl("/asset/$address.png")
)
