package fr.hadaly.core.service

import arrow.core.Either
import fr.hadaly.core.model.SimpleToken

interface TokenRepository {
        suspend fun getTokenByAddress(address: String): Either<Throwable, SimpleToken>

        suspend fun addToken(token: SimpleToken)

        suspend fun updateToken(token: SimpleToken)
}
