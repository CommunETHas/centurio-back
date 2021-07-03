package fr.hadaly.engine.di

import fr.hadaly.core.model.ChainNetwork
import fr.hadaly.engine.RecommandationEngine
import org.koin.core.qualifier.named
import org.koin.dsl.module

val engineModule = module {
    single { RecommandationEngine(get(named("${ChainNetwork.KOVAN}")), get()) }
}

