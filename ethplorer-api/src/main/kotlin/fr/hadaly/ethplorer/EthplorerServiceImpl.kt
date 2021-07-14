package fr.hadaly.ethplorer

import arrow.core.Either
import fr.hadaly.core.model.EthereumChain
import fr.hadaly.core.service.Configuration
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
    configuration: Configuration
) : EthplorerService {
    private val logger = LoggerFactory.getLogger(EthplorerServiceImpl::class.java)
    private val apiKey = configuration.getString(Configuration.Name.ETHPLORER_APIKEY)
    private val baseUrl: String = getBaseUrl(configuration)

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
                parameter("apiKey", apiKey)
            }.toWalletInfo()
        }
    }

    override suspend fun getTokenInfo(address: String): Either<Throwable, TokenInfo> {
        val url = "$baseUrl/getTokenInfo/$address"
        return Either.catch {
            client.get<TokenInfo>(url) {
                parameter("apiKey", apiKey)
            }
        }
    }

    private fun getBaseUrl(configuration: Configuration) =
        when (val ethChain = configuration.getString(Configuration.Name.ETHEREUM_CHAIN).uppercase()) {
            EthereumChain.MAINNET.name -> "http://api.ethplorer.io"
            EthereumChain.KOVAN.name -> "http://kovan-api.ethplorer.io"
            else -> throw IllegalArgumentException("$ethChain is not supported yet.")
        }.also {
            logger.info("Using $it Ethplorer API")
        }
}
