package fr.hadaly.core.service

import arrow.core.Either
import fr.hadaly.core.model.Recommandations

interface RecommandationEngine {
    suspend fun recommendFor(address: String): Either<Throwable, Recommandations>
}
