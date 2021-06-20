package fr.hadaly.di

import RecommandationEngine
import fr.hadaly.service.CoverService
import fr.hadaly.service.CoverServiceImpl
import org.koin.dsl.module

val restapiModule = module {
    single<CoverService> { CoverServiceImpl() }
    single { RecommandationEngine() }
}
