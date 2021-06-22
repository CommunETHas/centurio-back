package fr.hadaly.ethplorer

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

    override suspend fun getWalletInfo(address: String): WalletInfo {
        val url = "$baseUrl/getAddressInfo/$address"
        val addressInfo = client.get<AddressInfo>(url) {
            parameter("apiKey", "freekey")
        }
        return addressInfo.toWalletInfo()
    }

    override suspend fun getTokenInfo(address: String): TokenInfo {
        val url = "$baseUrl/getTokenInfo/$address"
        val tokenInfo = client.get<TokenInfo>(url) {
            parameter("apiKey", "freekey")
        }
        return tokenInfo
    }
}
