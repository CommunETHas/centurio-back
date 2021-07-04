package fr.hadaly

import fr.hadaly.di.restApiModule
import fr.hadaly.engine.di.engineModule
import fr.hadaly.ethplorer.di.ethplorerApiModule
import fr.hadaly.module.authenticationModule
import fr.hadaly.nexusapi.di.nexusApiModule
import fr.hadaly.notification.di.notificationModule
import fr.hadaly.persistence.di.persistenceModule
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
import org.koin.core.qualifier.named
import org.koin.dsl.module
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
            module { single { environment.config } },
            restApiModule,
            nexusApiModule,
            ethplorerApiModule,
            persistenceModule,
            engineModule,
            notificationModule
        )
    }

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

    install(CORS) {
        if (isDev) {
            anyHost()
            method(HttpMethod.Post)
            method(HttpMethod.Put)
            header(HttpHeaders.Authorization)
            header(HttpHeaders.AccessControlAllowCredentials)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowNonSimpleContentTypes = true
            allowCredentials = true
        } else {
            val frontHost = environment.config.property("ktor.deployment.front_host").getString()
            host(frontHost, schemes = listOf("https"))
        }
    }

    install(Routing) {
        index()
        cover(get(), get(named(network)) { parametersOf(storageUrl) })
        token(get())
        user(get())
        authentication(get(), jwtConfig)
        admin(get { parametersOf(network) })
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
