package fr.hadaly.core.model

import io.ktor.util.*
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val address: String,
    val nonce: String = generateNonce(),
    @Required
    val email: String? = null,
    val subscribed: Boolean = false
)

fun User.toPublicUser() = this.copy(email = "")


