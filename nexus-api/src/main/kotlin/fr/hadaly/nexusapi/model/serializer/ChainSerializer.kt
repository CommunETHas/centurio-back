package fr.hadaly.nexusapi.model.serializer

import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import fr.hadaly.nexusapi.model.Chain

@Serializer(forClass = Chain::class)
object ChainSerializer {
    override fun deserialize(decoder: Decoder): Chain {
        return Chain.valueOf(decoder.decodeString().uppercase())
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Chain", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Chain) {
        encoder.encodeString(value.toString().lowercase())
    }
}
