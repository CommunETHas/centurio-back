package fr.hadaly.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Recommandation(
    val cover: Cover,
    val reasoning: List<Reasoning>
)
