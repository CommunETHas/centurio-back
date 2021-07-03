package fr.hadaly.ethplorer.model.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigInteger

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = BigInteger::class)
class BigIntegerSerializer {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("BigInteger", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): BigInteger {
        return BigInteger(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: BigInteger) {
        encoder.encodeString(value.toString())
    }
}
