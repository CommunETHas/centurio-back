package fr.hadaly

import fr.hadaly.core.di.coreModule
import fr.hadaly.di.restApiModule
import fr.hadaly.engine.di.engineModule
import fr.hadaly.ethplorer.di.ethplorerApiModule
import fr.hadaly.module.authenticationModule
import fr.hadaly.module.corsModule
import fr.hadaly.module.koinModules
import fr.hadaly.persistence.service.DatabaseFactory
import fr.hadaly.util.JsonMapper
import fr.hadaly.util.JwtConfig
import fr.hadaly.web.admin
import fr.hadaly.web.authentication
import fr.hadaly.web.cover
import fr.hadaly.web.index
import fr.hadaly.web.token
import fr.hadaly.web.user
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get

@Suppress("unused") // Referenced in application.conf
@ExperimentalCoroutinesApi
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)

    install(ContentNegotiation) {
        json(JsonMapper.defaultMapper)
    }

    koinModules()

    val jwtConfig: JwtConfig = get { parametersOf(environment.config) }
    authenticationModule(jwtConfig)

    when {
        isDev -> {
            DatabaseFactory.connectAndMigrate()
        }
        isProd -> {
            val dbUrl = System.getenv("DATABASE_URL")
            DatabaseFactory.connectAndMigrate(dbUrl)
        }
    }

    corsModule()

    install(Routing) {
        index()
        cover(coverRepository = get(), engine = get())
        token(tokenRequestHandler = get())
        user(get())
        authentication(authenticationRequestHandler = get(), jwtConfig = jwtConfig)
        admin(notificationService = get())
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

val Application.envKind get() = environment.config.property("ktor.deployment.environment").getString()
val Application.isDev get() = envKind == "dev"
val Application.isProd get() = envKind != "dev"
val Application.storageUrl get() = environment.config.property("ktor.deployment.storage").getString()
val Application.network get() = environment.config.property("ktor.deployment.network").getString()
