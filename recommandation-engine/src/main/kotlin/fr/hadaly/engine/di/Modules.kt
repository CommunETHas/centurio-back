package fr.hadaly.engine.di

import fr.hadaly.engine.RecommandationEngine
import org.koin.dsl.module

val engineModule = module {
    single { RecommandationEngine(get(), get()) }
}

