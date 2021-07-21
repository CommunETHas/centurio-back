package fr.hadaly.persistence.di

import fr.hadaly.core.datasource.LocalDataSource
import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.UserRepository
import fr.hadaly.persistence.service.CoverLocalDataSource
import fr.hadaly.persistence.service.TokenLocalDataSourceImpl
import fr.hadaly.persistence.service.UserServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val persistenceModule = module {
    single<LocalDataSource<Cover, Int>>(named("coverPersistence")) { CoverLocalDataSource() }
    single<LocalDataSource<SimpleToken, Int>>(named("tokenPersistence")) {
        TokenLocalDataSourceImpl()
    }
    single<UserRepository> { UserServiceImpl() }
}

