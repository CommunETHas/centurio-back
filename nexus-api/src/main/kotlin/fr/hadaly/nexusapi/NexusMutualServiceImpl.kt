package fr.hadaly.nexusapi

import arrow.core.Either
import fr.hadaly.core.datasource.RemoteDataSource
import fr.hadaly.core.model.Cover
import fr.hadaly.nexusapi.model.CoverInfo
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class NexusMutualServiceImpl : NexusMutalService, RemoteDataSource<Cover, String> {
    private val baseUrl = "https://api.nexusmutual.io"
    private val client = HttpClient {
        serializer<Serializer>()
    }

    override suspend fun getCoverContracts(): Map<String, CoverInfo> {

        val url = "$baseUrl/coverables/contracts.json"
        val response = client.get<HttpStatement>(url)

        return response.execute { response ->
            val data = withContext(Dispatchers.IO) {
                val json = readAndTransform(response.content) { line ->
                    line?.replace("\"true\"", "true")
                }
                val data = Json.decodeFromString<Map<String, CoverInfo>>(json)
                data
            }
            data
        }
    }

    override suspend fun getAll(): List<Cover> =
        getCoverContracts().filter { !it.value.deprecated }.map { (address, coverInfo) ->
            coverInfo.toCover(address)
        }

    override suspend fun getById(id: String): Either<Throwable, Cover> =
        Either.catch {
            getCoverContracts()
                .filter { it.key.lowercase() == id.lowercase() }
                .firstNotNullOf { it.value.toCover(it.key) }
        }
}
