package fr.hadaly.ethplorer.di

import fr.hadaly.ethplorer.EthplorerService
import fr.hadaly.ethplorer.EthplorerServiceImpl
import org.koin.dsl.module

val ethplorerApiModule = module {
    factory<EthplorerService> { EthplorerServiceImpl() }
}



