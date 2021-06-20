package fr.hadaly.model

import fr.hadaly.nexusapi.model.Chain
import fr.hadaly.nexusapi.model.CoverType
import fr.hadaly.nexusapi.model.serializer.DateAddedSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    val name: String,
    val address: String,
    val type: CoverType,
    val supportedChains: List<Chain> = emptyList(),
    @Serializable(with = DateAddedSerializer::class)
    val logo: String? = null,
    val coveredToken: String? = null
)
