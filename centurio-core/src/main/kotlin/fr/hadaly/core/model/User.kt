package fr.hadaly.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val address: String,
    val nonce: String,
    val email: String = "",
    val subscribed: Boolean = false
)

fun User.toPublicUser() = this.copy(email = "")


