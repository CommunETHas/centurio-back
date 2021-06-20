package fr.hadaly

import fr.hadaly.nexusapi.di.nexusApiModule
import fr.hadaly.di.restapiModule
import fr.hadaly.service.DatabaseFactory
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.websocket.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import fr.hadaly.util.JsonMapper
import fr.hadaly.web.cover
import fr.hadaly.web.index
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get
import org.koin.logger.slf4jLogger

@Suppress("unused") // Referenced in application.conf
@ExperimentalCoroutinesApi
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(WebSockets)

    install(ContentNegotiation) {
        json(JsonMapper.defaultMapper)
    }

    install(Koin) {
        slf4jLogger()
        modules(restapiModule, nexusApiModule)
    }

    DatabaseFactory.connectAndMigrate()

    install(Routing) {
        index()
        cover(get(), get())
    }

}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}
