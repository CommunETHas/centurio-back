package fr.hadaly.nexusapi.model.serializer

import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

@Serializer(forClass = Date::class)
object DateAddedSerializer {
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd")

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DateAdded", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        return dateFormatter.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(dateFormatter.format(value))
    }
}
