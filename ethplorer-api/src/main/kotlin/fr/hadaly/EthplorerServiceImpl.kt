package fr.hadaly

import fr.hadaly.model.WalletInfo
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializer
import kotlinx.serialization.serializer


class EthplorerServiceImpl : EthplorerService {
    private val baseUrl = "http://api.ethplorer.io"
    private val client = HttpClient {
        serializer<Serializer>()
    }

    override suspend fun getWalletInfo(address: String): WalletInfo {
        val url = "$baseUrl/getAddressInfo/$address"
        return client.get(url) {
            parameter("apiKey", "freekey")
        }
    }
}
