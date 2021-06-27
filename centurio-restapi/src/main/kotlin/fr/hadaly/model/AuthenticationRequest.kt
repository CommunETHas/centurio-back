package fr.hadaly.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    val address: String,
    val signature: String
)
