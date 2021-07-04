package fr.hadaly.ethplorer

import arrow.core.Either
import fr.hadaly.ethplorer.model.AddressInfo
import fr.hadaly.ethplorer.model.TokenInfo
import fr.hadaly.ethplorer.model.WalletInfo
import fr.hadaly.ethplorer.model.toWalletInfo
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.LoggerFactory

class EthplorerServiceImpl(
    chain: String = "MAINNET"
) : EthplorerService {
    private val logger = LoggerFactory.getLogger(EthplorerServiceImpl::class.java)

    private val baseUrl: String = when(chain) {
        "MAINNET" -> "http://api.ethplorer.io"
        "KOVAN" -> "http://kovan-api.ethplorer.io"
        else -> throw IllegalArgumentException("$chain is not supported yet.")
    }.also {
        logger.info("Using $it Ethplorer API")
    }

    private val client = HttpClient {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
            accept(ContentType.Application.Json)
        }
    }

    override suspend fun getWalletInfo(address: String): Either<Throwable, WalletInfo> {
        val url = "$baseUrl/getAddressInfo/$address"
        return Either.catch {
            client.get<AddressInfo>(url) {
                parameter("apiKey", "freekey")
            }.toWalletInfo()
        }
    }

    override suspend fun getTokenInfo(address: String): Either<Throwable, TokenInfo> {
        val url = "$baseUrl/getTokenInfo/$address"
        return Either.catch {
            client.get<TokenInfo>(url) {
                parameter("apiKey", "freekey")
            }
        }
    }
}
