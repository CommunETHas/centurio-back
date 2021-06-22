package fr.hadaly.core.service

import fr.hadaly.core.model.SimpleToken

interface TokenRepository {
        suspend fun getTokenByAddress(address: String): SimpleToken

        suspend fun addToken(token: SimpleToken)

        suspend fun updateToken(token: SimpleToken)
}
