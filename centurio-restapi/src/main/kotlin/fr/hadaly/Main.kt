package fr.hadaly

import fr.hadaly.engine.di.engineModule
import fr.hadaly.ethplorer.di.ethplorerApiModule
import fr.hadaly.persistence.di.persistenceModule
import fr.hadaly.nexusapi.di.nexusApiModule
import fr.hadaly.persistence.service.DatabaseFactory
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import fr.hadaly.util.JsonMapper
import fr.hadaly.web.cover
import fr.hadaly.web.index
import fr.hadaly.web.token
import io.ktor.auth.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get
import org.koin.logger.slf4jLogger

@Suppress("unused") // Referenced in application.conf
@ExperimentalCoroutinesApi
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Authentication)

    install(ContentNegotiation) {
        json(JsonMapper.defaultMapper)
    }

    install(Koin) {
        slf4jLogger()
        modules(
            nexusApiModule,
            ethplorerApiModule,
            persistenceModule,
            engineModule
        )
    }

    when {
        isDev -> {
            DatabaseFactory.connectAndMigrate()
        }
        isProd -> {
            val dbUrl = System.getenv("DATABASE_URL")
            DatabaseFactory.connectAndMigrate(dbUrl)
        }
    }

    install(CORS) {
        if (envKind == "dev")
            anyHost()
    }

    install(Routing) {
        index()
        cover(get(), get())
        token(get(), get())
    }

}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

val Application.envKind get() = environment.config.property("ktor.deployment.environment").getString()
val Application.isDev get() = envKind == "dev"
val Application.isProd get() = envKind != "dev"
