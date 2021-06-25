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

class EthplorerServiceImpl : EthplorerService {
    private val baseUrl = "http://api.ethplorer.io"
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
