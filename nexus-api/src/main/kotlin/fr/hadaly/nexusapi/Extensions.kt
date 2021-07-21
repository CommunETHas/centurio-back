package fr.hadaly.nexusapi

import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.CoverType
import fr.hadaly.core.toSupportedChain
import fr.hadaly.nexusapi.model.CoverInfo
import io.ktor.utils.io.*
import java.nio.channels.ReadableByteChannel

fun CoverInfo.toCover(address: String) = Cover(
    name = name,
    address = address,
    type = CoverType.valueOf(type.name),
    supportedChains = supportedChains.map { it.toSupportedChain() },
    logo = logo,
    coveredToken = coveredToken
)

suspend inline fun readAndTransform(channel: ByteReadChannel, transform: (String?) -> String?): String {
    return StringBuilder().apply {
        do {
            val line = channel.readUTF8Line()
            transform(line)?.let(::append)
        } while (line != null)
    }.toString()
}

