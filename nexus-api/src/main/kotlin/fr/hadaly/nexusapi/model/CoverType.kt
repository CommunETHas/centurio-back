package fr.hadaly.nexusapi.model

import fr.hadaly.nexusapi.model.serializer.CoverTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = CoverTypeSerializer::class)
enum class CoverType {
    PROTOCOL,
    TOKEN,
    CUSTODIAN
}
