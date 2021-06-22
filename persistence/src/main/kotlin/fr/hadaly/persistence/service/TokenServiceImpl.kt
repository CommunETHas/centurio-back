package fr.hadaly.persistence.service

import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.persistence.entity.*
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toToken
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class TokenServiceImpl : TokenRepository {
    suspend fun getAllTokens(): List<TokenEntity> = dbQuery {
        TokenEntity.all().toList()
    }

    suspend fun getToken(id: Int): TokenEntity = dbQuery {
        TokenEntity[id]
    }

    override suspend fun getTokenByAddress(address: String) = dbQuery {
        TokenEntity.find { Tokens.address eq address }.first().toToken()
    }

    suspend fun deleteToken(id: Int): Boolean = dbQuery {
        val result = Tokens.deleteWhere { Tokens.id eq id }
        result > 0
    }

    override suspend fun updateToken(simpleToken: SimpleToken) {
        dbQuery {
            val tokenEntity = TokenEntity.find { Tokens.address eq simpleToken.address }.first().apply {
                address = simpleToken.address
                name = simpleToken.name
                known = true
            }
            simpleToken.recommendedCovers.forEach { recommandation ->
                TokensCovers.insert {
                    it[token] = tokenEntity.id
                    it[cover] = CoverEntity.find { Covers.address eq recommandation.address }.first().id
                }
            }
        }
    }

    override suspend fun addToken(token: SimpleToken) {
        dbQuery {
            TokenEntity.new {
                name = token.name
                address = token.address
                owner = token.owner
                symbol = token.symbol
            }
        }
    }
}
