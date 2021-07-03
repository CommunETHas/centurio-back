package fr.hadaly.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequest(
    val frequency: String,
    val address: String,
    val email: String
)
