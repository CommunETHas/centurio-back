package fr.hadaly.web

import fr.hadaly.nexusapi.NexusMutualServiceImpl
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.index() {

    val indexPage = javaClass.getResource("/index.html").readText()

    get("/") {
        val nexus = NexusMutualServiceImpl()
        val contracts = nexus.getCoverContracts()
        call.respondText(indexPage, ContentType.Text.Html)
    }
}
