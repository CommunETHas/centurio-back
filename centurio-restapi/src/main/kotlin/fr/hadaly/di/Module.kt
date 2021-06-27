package fr.hadaly.di

import fr.hadaly.handler.AuthenticationRequestHandler
import fr.hadaly.handler.TokenRequestHandler
import fr.hadaly.util.JwtConfig
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.engine.*
import org.koin.dsl.module

val restApiModule = module {
    factory { (config: ApplicationConfig) -> JwtConfig(config) }
    factory {
        TokenRequestHandler(
            tokenRepository = get(),
            coverRepository = get(),
            ethplorerService = get()
        )
    }
    factory { AuthenticationRequestHandler() }
}
