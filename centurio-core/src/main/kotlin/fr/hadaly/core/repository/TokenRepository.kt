package fr.hadaly.core.repository

import arrow.core.Either
import fr.hadaly.core.model.SimpleToken

interface TokenRepository {
        suspend fun getTokens(): List<SimpleToken>

        suspend fun getTokenByAddress(address: String): Either<Throwable, SimpleToken>

        suspend fun addToken(token: SimpleToken)

        suspend fun updateToken(token: SimpleToken)

        suspend fun addTokens(tokens: Iterable<SimpleToken>)

        suspend fun updateTokens(tokens: Iterable<SimpleToken>)
}
