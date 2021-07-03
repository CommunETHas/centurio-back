package fr.hadaly.core.model

import fr.hadaly.core.ResourceUrlSerializer
import fr.hadaly.nexusapi.model.Chain
import fr.hadaly.nexusapi.model.CoverType
import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    val name: String,
    val address: String,
    val type: CoverType,
    val supportedChains: List<Chain> = emptyList(),
    val logo: String? = null,
    @Serializable(with = ResourceUrlSerializer::class)
    val logoUrl: ResourceUrl = ResourceUrl("/cover/$logo"),
    val coveredToken: String? = null
)
