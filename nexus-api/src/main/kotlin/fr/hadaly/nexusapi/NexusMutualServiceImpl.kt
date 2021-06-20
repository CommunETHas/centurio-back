package fr.hadaly.nexusapi

import fr.hadaly.nexusapi.model.Cover
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.io.path.outputStream
import kotlin.io.path.readText

class NexusMutualServiceImpl: NexusMutalService {
    private val baseUrl = "https://api.staging.nexusmutual.io"
    private val client = HttpClient {
        serializer<Serializer>()
    }

    override suspend fun getCoverContracts() : Map<String, Cover> {

        val url = "$baseUrl/coverables/contracts.json"
        val response = client.get<HttpStatement>(url)
        val f = kotlin.io.path.createTempFile("contracts.json")

        return response.execute { response ->
            val data = withContext(Dispatchers.IO) {
                response.content.copyTo(f.outputStream())
                val st = f.readText().replace("\"deprecated\": \"true\"", "\"deprecated\": true")
                val data = Json.decodeFromString<Map<String, Cover>>(st)
                data
            }
            data
        }
    }
}
