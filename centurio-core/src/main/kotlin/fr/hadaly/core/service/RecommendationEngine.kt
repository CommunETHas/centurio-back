package fr.hadaly.core.service

import arrow.core.Either
import fr.hadaly.core.model.Recommandations

interface RecommendationEngine {
    suspend fun recommendFor(address: String): Either<Throwable, Recommandations>
}
