package fr.hadaly.core

import fr.hadaly.core.model.ResourceUrl
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.Configuration
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Serializer(forClass = ResourceUrl::class)
object ResourceUrlSerializer: KoinComponent {
    val config: Configuration by inject()
    val prefix = config.getString("ktor.deployment.storage")

    override fun deserialize(decoder: Decoder): ResourceUrl {
        return ResourceUrl("$prefix/${decoder.decodeString()}")
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ResourceUrl", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ResourceUrl) {
        encoder.encodeString("$prefix${value.value.lowercase()}")
    }
}
