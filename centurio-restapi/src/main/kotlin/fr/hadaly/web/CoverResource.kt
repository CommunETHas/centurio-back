package fr.hadaly.web

import arrow.core.Either
import fr.hadaly.core.service.CoverRepository
import fr.hadaly.core.service.RecommendationEngine
import fr.hadaly.persistence.service.CoverService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.util.*

@ExperimentalCoroutinesApi
fun Route.cover(coverRepository: CoverRepository, engine: RecommendationEngine) {

    route("/cover") {

        get {
            call.respond(coverRepository.getAllCovers())
        }

        get("/recommend/{wallet}") {
            if (call.parameters.contains("wallet")) {
                val wallet = call.parameters.getOrFail("wallet")
                when(val recommandations = engine.recommendFor(wallet)) {
                    is Either.Left -> {
                        call.respond(HttpStatusCode.BadRequest, recommandations.value.localizedMessage)
                    }
                    is Either.Right -> {
                        call.respond(recommandations.value)
                    }
                }
            }
        }
    }

}
