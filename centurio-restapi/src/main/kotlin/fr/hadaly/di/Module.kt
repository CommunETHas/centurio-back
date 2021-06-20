package fr.hadaly.di

import RecommandationEngine
import fr.hadaly.service.CoverService
import org.koin.dsl.module

val restapiModule = module {
    single { CoverService() }
    single { RecommandationEngine() }
}
