package fr.hadaly.engine.di

import fr.hadaly.core.model.SupportedChain
import fr.hadaly.engine.RecommandationEngine
import org.koin.core.qualifier.named
import org.koin.dsl.module

val engineModule = module {
    single { RecommandationEngine(get(named("${SupportedChain.KOVAN}")), get()) }
}

