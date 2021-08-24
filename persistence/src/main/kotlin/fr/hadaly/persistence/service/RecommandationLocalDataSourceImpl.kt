package fr.hadaly.persistence.service

import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.persistence.entity.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class RecommandationLocalDataSourceImpl {

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
}
