package fr.hadaly.ethplorer.di

import fr.hadaly.ethplorer.EthplorerService
import fr.hadaly.ethplorer.EthplorerServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ethplorerApiModule = module {
    factory<EthplorerService>(named("MAINNET")) { EthplorerServiceImpl(configuration = get()) }
    factory<EthplorerService>(named("KOVAN")) { EthplorerServiceImpl("KOVAN", configuration = get()) }
}



