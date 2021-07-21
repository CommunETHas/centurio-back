package fr.hadaly.engine.di

import fr.hadaly.core.service.RecommendationEngine
import fr.hadaly.engine.RecommendationEngineImpl
import org.koin.dsl.module

val engineModule = module {
    single<RecommendationEngine> {
        RecommendationEngineImpl(
            walletService = get(),
            tokenRepository = get()
        )
    }
}

