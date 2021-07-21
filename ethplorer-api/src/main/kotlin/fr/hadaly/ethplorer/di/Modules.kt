package fr.hadaly.ethplorer.di

import fr.hadaly.core.datasource.RemoteDataSource
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.WalletService
import fr.hadaly.ethplorer.EthplorerServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ethplorerApiModule = module {
    factory<RemoteDataSource<SimpleToken, String>>(named("ethplorer")) {
        EthplorerServiceImpl(configuration = get())
    }

    factory<WalletService> {
        EthplorerServiceImpl(configuration = get())
    }
}



