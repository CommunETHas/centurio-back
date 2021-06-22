package fr.hadaly.ethplorer

import fr.hadaly.ethplorer.model.TokenInfo
import fr.hadaly.ethplorer.model.WalletInfo

interface EthplorerService {
    suspend fun getWalletInfo(address: String): WalletInfo
    suspend fun getTokenInfo(address: String): TokenInfo
}
