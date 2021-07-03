package fr.hadaly.engine.di

import fr.hadaly.core.model.ChainNetwork
import fr.hadaly.engine.RecommandationEngine
import org.koin.core.qualifier.named
import org.koin.dsl.module

val engineModule = module {
    single(named("kovan")) { RecommandationEngine(get(named("${ChainNetwork.KOVAN}")), get()) }
    single(named("mainnet")) { RecommandationEngine(get(named("${ChainNetwork.MAINNET}")), get()) }
}

