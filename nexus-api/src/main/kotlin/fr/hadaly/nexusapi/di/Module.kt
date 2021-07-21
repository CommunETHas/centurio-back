package fr.hadaly.nexusapi.di

import fr.hadaly.core.datasource.RemoteDataSource
import fr.hadaly.core.model.Cover
import fr.hadaly.nexusapi.NexusMutualServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val nexusApiModule = module {
    single<RemoteDataSource<Cover, String>>(named("nexus")) { NexusMutualServiceImpl() }
}
