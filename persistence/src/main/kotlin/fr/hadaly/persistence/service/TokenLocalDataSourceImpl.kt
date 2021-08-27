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
import fr.hadaly.persistence.entity.UnderlyingToken
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toToken
import org.jetbrains.exposed.sql.*
import org.slf4j.LoggerFactory

class TokenLocalDataSourceImpl : LocalDataSource<SimpleToken, String> {
    private val logger = LoggerFactory.getLogger(TokenLocalDataSourceImpl::class.java)

    override suspend fun getAll(): List<SimpleToken> = dbQuery {
        TokenEntity.all().map { it.toToken() }
    }

    override suspend fun getById(id: String): Either<Throwable, SimpleToken> = dbQuery {
        Either.catch {
            val token = TokenEntity.find { Tokens.address.lowerCase() eq id.lowercase() }.first().toToken()
            logger.debug("Token is $token")
            token
        }
    }

    override suspend fun getByKey(key: Pair<String, String>): Either<Throwable, SimpleToken> = dbQuery {
        Either.catch {
            when (key.first) {
                "address" -> {
                    val token = TokenEntity.find { Tokens.address eq key.second }.first().toToken()
                    logger.debug("Token is $token")
                    token
                }
                else -> throw NoSuchElementException("Unsupported key ${key.second}")
            }
        }
    }

    override suspend fun delete(id: String): Boolean = dbQuery {
        val result = Tokens.deleteWhere { Tokens.address.lowerCase() eq id.lowercase() }
        result > 0
    }

    override suspend fun update(token: SimpleToken) {
        dbQuery { updateToken(token) }
    }

    override suspend fun addAll(tokens: Iterable<SimpleToken>) = dbQuery {
        val entities = tokens.map { token ->
            val entity = TokenEntity.new {
                name = token.name
                address = token.address
                owner = token.owner
                symbol = token.symbol
            }
            token.recommendedCovers.forEach { recommendation ->
                addRecommandation(token, recommendation)
            }

            val recommandations = CoverEntity.find {
                Covers.id inList TokensCovers.select { TokensCovers.token eq entity.id }.map {
                    it[TokensCovers.cover]
                }
            }
            entity.apply {
                recommendedCovers = recommandations
            }
        }
    }

    override suspend fun updateAll(tokens: Iterable<SimpleToken>) = dbQuery {
        tokens.forEach { token -> updateToken(token) }
    }

    private suspend fun addRecommandation(
        tokenEntity: SimpleToken,
        recommendation: Cover
    ) = dbQuery {
        if (!tokenEntity.recommendedCovers.contains(recommendation)) {
            TokensCovers.insert {
                it[token] = TokenEntity.find {
                    Tokens.address.lowerCase() eq tokenEntity.address.lowercase()
                }.first().id
                it[cover] = CoverEntity.find {
                    Covers.address.lowerCase() eq recommendation.address.lowercase()
                }.first().id
            }
        }
    }

    override suspend fun add(token: SimpleToken) {
        dbQuery {
            val tokenEntity = TokenEntity.new {
                name = token.name
                address = token.address
                owner = token.owner
                symbol = token.symbol
            }

            token.underlyingTokens.forEach { simpleToken ->
                UnderlyingToken.new {
                    parent = tokenEntity.id
                    child =
                        TokenEntity.find { Tokens.address.lowerCase() eq simpleToken.address.lowercase() }.first().id
                }
            }
        }
    }

    private suspend fun updateToken(token: SimpleToken) {
        val tokenEntity = TokenEntity.find { Tokens.address eq token.address }
            .first().apply {
                address = token.address
                name = token.name
                known = true
            }

        token.recommendedCovers.forEach { recommendation ->
            addRecommandation(token, recommendation)
        }

        token.underlyingTokens.forEach { simpleToken ->
            UnderlyingToken.new {
                parent = tokenEntity.id
                child =
                    TokenEntity.find { Tokens.address.lowerCase() eq simpleToken.address.lowercase() }.first().id
            }
        }
    }
}
