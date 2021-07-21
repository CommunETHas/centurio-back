package fr.hadaly.core.model

import fr.hadaly.core.ResourceUrlSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    val name: String,
    val address: String,
    val type: CoverType,
    val supportedChains: List<SupportedChain> = emptyList(),
    val logo: String? = null,
    @Serializable(with = ResourceUrlSerializer::class)
    val logoUrl: ResourceUrl = ResourceUrl("/cover/$logo"),
    val coveredToken: String? = null
)

@Serializable
data class SimpleCover(
    val name: String,
    val address: String,
    val type: CoverType,
    val supportedChains: List<SupportedChain> = emptyList(),
    val logo: String? = null,
    @Serializable(with = ResourceUrlSerializer::class)
    val logoUrl: ResourceUrl = ResourceUrl("/cover/$logo"),
    val coveredToken: String? = null
)

fun Cover.toSimpleCover() = SimpleCover(name, address, type, supportedChains, logo, logoUrl, coveredToken)
