package fr.hadaly.engine.di

import fr.hadaly.core.model.ChainNetwork
import fr.hadaly.core.service.RecommandationEngine
import fr.hadaly.engine.RecommandationEngineImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val engineModule = module {
    single<RecommandationEngine>(named("kovan")) { RecommandationEngineImpl(get(named("${ChainNetwork.KOVAN}")), get()) }
    single<RecommandationEngine>(named("mainnet")) { RecommandationEngineImpl(get(named("${ChainNetwork.MAINNET}")), get()) }
}

