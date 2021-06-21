package fr.hadaly

import fr.hadaly.model.AddressInfo
import fr.hadaly.model.WalletInfo

interface EthplorerService {
    suspend fun getWalletInfo(address: String): WalletInfo
}
