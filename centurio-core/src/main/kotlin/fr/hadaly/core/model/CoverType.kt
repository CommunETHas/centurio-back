package fr.hadaly.core.model

import fr.hadaly.core.model.serializer.CoverTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = CoverTypeSerializer::class)
enum class CoverType {
    PROTOCOL,
    TOKEN,
    CUSTODIAN
}
