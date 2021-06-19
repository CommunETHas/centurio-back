package fr.hadaly.model.serializer

import fr.hadaly.model.Chain
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.web3j.abi.datatypes.Address

@Serializer(forClass = Address::class)
object AddressSerializer {
    override fun deserialize(decoder: Decoder): Address {
        return Address(decoder.decodeString())
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Address", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Address) {
        encoder.encodeString(value.toString().lowercase())
    }
}
