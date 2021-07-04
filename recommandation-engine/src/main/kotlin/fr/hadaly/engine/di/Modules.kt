package fr.hadaly.engine.di

import fr.hadaly.core.model.ChainNetwork
import fr.hadaly.core.service.RecommendationEngine
import fr.hadaly.engine.RecommendationEngineImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val engineModule = module {
    single<RecommendationEngine>(named("kovan")) {
        RecommendationEngineImpl(
            ethplorerService = get(named("${ChainNetwork.KOVAN}")),
            tokenService = get()
        )
    }
    single<RecommendationEngine>(named("mainnet")) {
        RecommendationEngineImpl(
            ethplorerService = get(named("${ChainNetwork.MAINNET}")),
            tokenService = get()
        )
    }
}

