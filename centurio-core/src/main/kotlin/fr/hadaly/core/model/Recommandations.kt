package fr.hadaly.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Recommandations(
    val count: Int = 0,
    val recommandations: List<Recommandation> = emptyList(),
    val unsuportedTokens: List<SimpleToken> = emptyList()
)
