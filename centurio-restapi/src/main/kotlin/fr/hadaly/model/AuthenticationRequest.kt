package fr.hadaly.model

import fr.hadaly.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequest(
    val user: User,
    val signature: String
)
