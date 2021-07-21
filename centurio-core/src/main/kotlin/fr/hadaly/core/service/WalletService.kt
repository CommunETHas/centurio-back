package fr.hadaly.core.service

import arrow.core.Either
import fr.hadaly.core.model.Wallet

interface WalletService {
    suspend fun getWallet(address: String): Either<Throwable, Wallet>
}
