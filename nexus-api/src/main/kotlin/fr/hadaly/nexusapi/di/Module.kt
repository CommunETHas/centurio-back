package fr.hadaly.nexusapi.di

import fr.hadaly.nexusapi.NexusMutalService
import fr.hadaly.nexusapi.NexusMutualServiceImpl
import org.koin.dsl.module

val nexusApiModule = module {
    single<NexusMutalService> { NexusMutualServiceImpl() }
}
