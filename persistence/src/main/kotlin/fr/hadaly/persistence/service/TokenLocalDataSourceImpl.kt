package fr.hadaly.persistence.service

import arrow.core.Either
import fr.hadaly.core.datasource.LocalDataSource
import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.persistence.entity.CoverEntity
import fr.hadaly.persistence.entity.Covers
import fr.hadaly.persistence.entity.TokenEntity
import fr.hadaly.persistence.entity.Tokens
import fr.hadaly.persistence.entity.TokensCovers
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toToken
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class TokenLocalDataSourceImpl : LocalDataSource<SimpleToken, Int> {

    override suspend fun getAll(): List<SimpleToken> = dbQuery {
        TokenEntity.all().map { it.toToken() }
    }

    override suspend fun getById(id: Int): Either<Throwable, SimpleToken> = dbQuery {
        Either.catch {  TokenEntity[id].toToken() }
    }

    override suspend fun getByKey(key: Pair<String, String>): Either<Throwable, SimpleToken> = dbQuery {
        Either.catch {
            when(key.first) {
                "address" -> TokenEntity.find { Tokens.address eq key.second }.first().toToken()
                else -> throw NoSuchElementException("Unsupported key ${key.second}")
            }
        }
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        val result = Tokens.deleteWhere { Tokens.id eq id }
        result > 0
    }

    override suspend fun update(token: SimpleToken) {
        dbQuery { updateToken(token) }
    }

    override suspend fun addAll(tokens: Iterable<SimpleToken>) = dbQuery {
        tokens.map { token ->
            TokenEntity.new {
                name = token.name
                address = token.address
                owner = token.owner
                symbol = token.symbol
            }
        }
        tokens.forEach { tokenEntity ->
            tokenEntity.recommendedCovers.forEach { recommendation ->
                addRecommandation(tokenEntity, recommendation)
            }
        }
    }

    override suspend fun updateAll(tokens: Iterable<SimpleToken>) = dbQuery {
        tokens.forEach { token -> updateToken(token) }
    }

    private suspend fun addRecommandation(
        tokenEntity: SimpleToken,
        recommendation: Cover
    ) {
        try {
            newSuspendedTransaction(Dispatchers.Default) {
                TokensCovers.insert {
                    it[token] = TokenEntity.find {
                        Tokens.address.lowerCase() eq tokenEntity.address.lowercase()
                    }.first().id
                    it[cover] = CoverEntity.find {
                        Covers.address.lowerCase() eq recommendation.address.lowercase()
                    }.first().id
                }
            }
        } catch (error: ExposedSQLException) {
            exposedLogger.info("Cover ${recommendation.name} is already registered for ${tokenEntity.symbol} token")
        }
    }

    override suspend fun add(token: SimpleToken) {
        dbQuery {
            TokenEntity.new {
                name = token.name
                address = token.address
                owner = token.owner
                symbol = token.symbol
            }
        }
    }

    private suspend fun updateToken(token: SimpleToken) {
        TokenEntity.find { Tokens.address eq token.address }
            .first().apply {
                address = token.address
                name = token.name
                known = true
            }

        token.recommendedCovers.forEach { recommendation ->
            addRecommandation(token, recommendation)
        }
    }
}
