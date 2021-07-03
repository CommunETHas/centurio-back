package fr.hadaly.core.model

import fr.hadaly.core.ResourceUrlSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Reasoning(
    val token: String,
    @Serializable(with = ResourceUrlSerializer::class)
    val logoUrl: ResourceUrl,
    val description: String
)
