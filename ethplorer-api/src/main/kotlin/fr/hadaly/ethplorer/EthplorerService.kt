package fr.hadaly.ethplorer

import arrow.core.Either
import fr.hadaly.ethplorer.model.TokenInfo
import fr.hadaly.ethplorer.model.WalletInfo

interface EthplorerService {
    suspend fun getWalletInfo(address: String): Either<Throwable, WalletInfo>
    suspend fun getTokenInfo(address: String): Either<Throwable, TokenInfo>
}
