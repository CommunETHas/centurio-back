package fr.hadaly.nexusapi.model

import kotlinx.serialization.Serializable

@Serializable
data class SupportedChain(
    val name: Chain,
    val logoUrl: String = "cover/$name.png"
)
