package fr.hadaly.core.model

import fr.hadaly.core.model.serializer.LocalDateTimeSerializer
import io.ktor.util.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class User(
    val address: String,
    val nonce: String = generateNonce(),
    val email: String? = null,
    val subscribed: Boolean = false,
    val frequency: String? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val notifiedAt: LocalDateTime? = null
)

fun User.toPublicUser() = this.copy(email = null, frequency = null, notifiedAt = null)


