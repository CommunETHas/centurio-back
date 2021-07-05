package fr.hadaly.persistence.service

import arrow.core.Either
import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.persistence.entity.CoverEntity
import fr.hadaly.persistence.entity.Covers
import fr.hadaly.persistence.entity.TokenEntity
import fr.hadaly.persistence.entity.Tokens
import fr.hadaly.persistence.entity.TokensCovers
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class TokenServiceImpl : TokenRepository {

    suspend fun getAllTokens(): List<TokenEntity> = dbQuery {
        TokenEntity.all().toList()
    }

    suspend fun getToken(id: Int): TokenEntity = dbQuery {
        TokenEntity[id]
    }

    override suspend fun getTokenByAddress(address: String): Either<Throwable, SimpleToken> = dbQuery {
        Either.catch {
            TokenEntity.find { Tokens.address eq address }.first().toToken()
        }
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
                insertRecommandationFor(tokenEntity, recommandation)
            }
        }
    }

    private suspend fun insertRecommandationFor(
        tokenEntity: TokenEntity,
        recommandation: Cover
    ) {
        try {
            newSuspendedTransaction(Dispatchers.Default) {
                TokensCovers.insert {
                    it[token] = tokenEntity.id
                    it[cover] = CoverEntity.find {
                        Covers.address.lowerCase() eq recommandation.address.lowercase()
                    }.first().id
                }
            }
        } catch (error: ExposedSQLException) {
            exposedLogger.info("Cover ${recommandation.name} is already registered for ${tokenEntity.symbol} token")
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
