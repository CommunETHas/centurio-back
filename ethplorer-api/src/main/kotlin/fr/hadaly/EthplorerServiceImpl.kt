package fr.hadaly

import fr.hadaly.model.AddressInfo
import fr.hadaly.model.WalletInfo
import fr.hadaly.model.toWalletInfo
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializer
import kotlinx.serialization.serializer


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
}
