package fr.hadaly.engine.di

import fr.hadaly.core.model.EthereumChain
import fr.hadaly.core.service.RecommendationEngine
import fr.hadaly.engine.RecommendationEngineImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val engineModule = module {
    single<RecommendationEngine> {
        RecommendationEngineImpl(
            ethplorerService = get(),
            tokenService = get()
        )
    }
}

