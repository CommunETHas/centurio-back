package fr.hadaly.di

import fr.hadaly.core.model.ChainNetwork
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
    factory { (network: String) ->
        TokenRequestHandler(
            tokenRepository = get(),
            coverRepository = get(),
            ethplorerService = get(named(network.uppercase()))
        )
    }
    factory { AuthenticationRequestHandler() }
    single<Configuration> { ConfigurationProvider(get()) }
}
