package fr.hadaly.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Reasoning(
    val token: String,
    val logoUrl: String,
    val description: String
)
