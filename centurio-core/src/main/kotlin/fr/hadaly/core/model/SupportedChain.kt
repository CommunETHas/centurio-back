package fr.hadaly.core.model

import fr.hadaly.core.ResourceUrlSerializer
import fr.hadaly.nexusapi.model.Chain
import kotlinx.serialization.Serializable

@Serializable
data class SupportedChain(
    val name: Chain,
    @Serializable(with = ResourceUrlSerializer::class)
    val logoUrl: ResourceUrl = ResourceUrl("/chain/$name.png")
)
