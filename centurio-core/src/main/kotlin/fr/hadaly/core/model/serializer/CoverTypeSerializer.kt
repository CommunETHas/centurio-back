package fr.hadaly.core.model.serializer

import fr.hadaly.core.model.CoverType
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = CoverType::class)
object CoverTypeSerializer {
    override fun deserialize(decoder: Decoder): CoverType {
        return CoverType.valueOf(decoder.decodeString().uppercase())
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("CoverType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: CoverType) {
        encoder.encodeString(value.toString().lowercase())
    }
}
