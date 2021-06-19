package fr.hadaly.model

import fr.hadaly.model.serializer.CoverTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = CoverTypeSerializer::class)
enum class CoverType {
    PROTOCOL,
    TOKEN,
    CUSTODIAN
}
