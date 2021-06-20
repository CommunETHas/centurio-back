package fr.hadaly

import fr.hadaly.service.DatabaseFactory
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import fr.hadaly.service.CoverService
import fr.hadaly.util.JsonMapper
import fr.hadaly.web.cover
import fr.hadaly.web.index

@Suppress("unused") // Referenced in application.conf
@ExperimentalCoroutinesApi
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(WebSockets)

    install(ContentNegotiation) {
        json(JsonMapper.defaultMapper)
    }

    DatabaseFactory.connectAndMigrate()

    val coverService = CoverService()

    install(Routing) {
        index()
        cover(coverService)
    }

}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}
