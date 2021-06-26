package fr.hadaly

import fr.hadaly.di.restApiModule
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
import fr.hadaly.util.JwtConfig
import fr.hadaly.web.cover
import fr.hadaly.web.index
import fr.hadaly.web.token
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.client.*
import io.ktor.client.features.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get
import org.koin.logger.slf4jLogger

@Suppress("unused") // Referenced in application.conf
@ExperimentalCoroutinesApi
fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)

    install(ContentNegotiation) {
        json(JsonMapper.defaultMapper)
    }

    install(Koin) {
        slf4jLogger()
        modules(
            restApiModule,
            nexusApiModule,
            ethplorerApiModule,
            persistenceModule,
            engineModule
        )
    }

    val jwtConfig: JwtConfig = get { parametersOf(environment.config) }

    install(Authentication) {
        jwt {
            realm = environment.config.property("ktor.jwt.realm").getString()
            verifier(jwtConfig.verifier)
            validate { JWTPrincipal(it.payload) }
        }
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
        if (isDev) {
            anyHost()
        } else {
            val frontHost = environment.config.property("ktor.deployment.front_host").getString()
            host(frontHost, schemes = listOf("https"))
        }
    }

    install(Routing) {
        index()
        cover(get(), get { parametersOf(storageUrl) })
        token(get())
    }

}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

val Application.envKind get() = environment.config.property("ktor.deployment.environment").getString()
val Application.isDev get() = envKind == "dev"
val Application.isProd get() = envKind != "dev"
val Application.storageUrl get() = environment.config.property("ktor.deployment.storage").getString()
