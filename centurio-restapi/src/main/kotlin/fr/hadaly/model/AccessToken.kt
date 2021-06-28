package fr.hadaly.model

import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    val accessToken: String
)
