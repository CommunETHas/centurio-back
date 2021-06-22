package fr.hadaly.persistence.service

import fr.hadaly.ethplorer.model.Token
import fr.hadaly.persistence.entity.TokenEntity

interface TokenService {
    suspend fun getAllTokens(): List<TokenEntity>

    suspend fun getToken(id: Int): TokenEntity

    suspend fun getTokenByAddress(address: String): TokenEntity

    suspend fun deleteToken(id: Int): Boolean

    suspend fun updateToken(token: TokenEntity): TokenEntity

    suspend fun addToken(token: Token): TokenEntity
}
