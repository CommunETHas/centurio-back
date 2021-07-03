package fr.hadaly.di

import fr.hadaly.core.model.SupportedChain
import fr.hadaly.core.service.Configuration
import fr.hadaly.handler.AuthenticationRequestHandler
import fr.hadaly.handler.TokenRequestHandler
import fr.hadaly.util.ConfigurationProvider
import fr.hadaly.util.JwtConfig
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.engine.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val restApiModule = module {
    factory { (config: ApplicationConfig) -> JwtConfig(config) }
    factory {
        TokenRequestHandler(
            tokenRepository = get(),
            coverRepository = get(),
            ethplorerService = get(named("${SupportedChain.KOVAN}"))
        )
    }
    factory { AuthenticationRequestHandler() }
    single<Configuration> { ConfigurationProvider(get()) }
}
