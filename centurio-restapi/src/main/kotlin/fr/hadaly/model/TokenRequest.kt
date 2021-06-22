package fr.hadaly.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val address: String,
    val name: String? = null,
    val symbol: String? = null,
    val covers: List<String>
)
