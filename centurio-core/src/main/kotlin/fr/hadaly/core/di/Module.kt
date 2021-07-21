package fr.hadaly.core.di

import fr.hadaly.core.repository.CoverRepositoryImpl
import fr.hadaly.core.repository.TokenRepository
import fr.hadaly.core.repository.TokenRepositoryImpl
import fr.hadaly.core.service.CoverRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModule = module {
    single<TokenRepository> {
        TokenRepositoryImpl(
            localDataSource = get(named("tokenPersistence")),
            remoteDataSource = get(named("ethplorer"))
        )
    }
    single<CoverRepository> {
        CoverRepositoryImpl(
            localDataSource = get(named("coverPersistence")),
            remoteDataSource = get(named("nexus"))
        )
    }
}
