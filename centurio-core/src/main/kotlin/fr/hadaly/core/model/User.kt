package fr.hadaly.core.model

import io.ktor.util.*
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val address: String,
    val nonce: String = generateNonce(),
    val email: String = "",
    val subscribed: Boolean = false
)

fun User.toPublicUser() = this.copy(email = "")


