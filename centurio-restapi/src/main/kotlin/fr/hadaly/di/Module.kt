package fr.hadaly.di

import fr.hadaly.core.service.Configuration
import fr.hadaly.handler.AuthenticationRequestHandler
import fr.hadaly.handler.TokenRequestHandler
import fr.hadaly.util.ConfigurationProvider
import fr.hadaly.util.JwtConfig
import io.ktor.config.*
import org.koin.core.qualifier.named
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
    single<Configuration> { ConfigurationProvider(get()) }
}
