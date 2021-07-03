package fr.hadaly.persistence.di

import fr.hadaly.core.service.CoverRepository
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.core.service.UserRepository
import fr.hadaly.persistence.service.CoverService
import fr.hadaly.persistence.service.CoverServiceImpl
import fr.hadaly.persistence.service.TokenServiceImpl
import fr.hadaly.persistence.service.UserServiceImpl
import org.koin.dsl.module

val persistenceModule = module {
    single<CoverRepository> { CoverServiceImpl() }
    single<TokenRepository> { TokenServiceImpl() }
    single<UserRepository> { UserServiceImpl() }
}

