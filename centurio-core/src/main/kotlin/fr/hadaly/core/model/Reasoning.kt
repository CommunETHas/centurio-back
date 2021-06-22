package fr.hadaly.core.model

import fr.hadaly.ethplorer.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class Reasoning(
    val token: String,
    val description: String
)
