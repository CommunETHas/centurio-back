package fr.hadaly.ethplorer

import arrow.core.Either
import fr.hadaly.core.datasource.RemoteDataSource
import fr.hadaly.core.model.EthereumChain
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.model.Wallet
import fr.hadaly.core.service.Configuration
import fr.hadaly.core.service.WalletService
import fr.hadaly.ethplorer.model.AddressInfo
import fr.hadaly.ethplorer.model.TokenInfo
import fr.hadaly.ethplorer.model.toWallet
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.slf4j.LoggerFactory

class EthplorerServiceImpl(
    configuration: Configuration
) : RemoteDataSource<SimpleToken, String>,
    WalletService {
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

    override suspend fun getWallet(address: String): Either<Throwable, Wallet> {
        val url = "$baseUrl/getAddressInfo/$address"
        return Either.catch {
            client.get<AddressInfo>(url) {
                parameter("apiKey", apiKey)
            }.toWallet()
        }
    }

    override suspend fun getById(address: String): Either<Throwable, SimpleToken> {
        val url = "$baseUrl/getTokenInfo/$address"
        return Either.catch {
            client.get<TokenInfo>(url) {
                parameter("apiKey", apiKey)
            }
        }.map { it.toSimpleToken() }
    }

    private fun getBaseUrl(configuration: Configuration) =
        when (val ethChain = configuration.getString(Configuration.Name.ETHEREUM_CHAIN).uppercase()) {
            EthereumChain.MAINNET.name -> "http://api.ethplorer.io"
            EthereumChain.KOVAN.name -> "http://kovan-api.ethplorer.io"
            else -> throw IllegalArgumentException("$ethChain is not supported yet.")
        }.also {
            logger.info("Using $it Ethplorer API")
        }

    override suspend fun getAll(): List<SimpleToken> {
        throw UnsupportedOperationException("Not yet supported")
    }
}
