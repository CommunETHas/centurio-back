package fr.hadaly.web

import fr.hadaly.core.service.CoverRepository
import fr.hadaly.engine.RecommandationEngine
import fr.hadaly.persistence.service.CoverService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.util.*

@ExperimentalCoroutinesApi
fun Route.cover(coverRepository: CoverRepository, engine: RecommandationEngine) {

    route("/cover") {

        get {
            call.respond(coverRepository.getAllCovers())
        }

        get("/recommend/{wallet}") {
            if (call.parameters.contains("wallet")) {
                val wallet = call.parameters.getOrFail("wallet")
                try {
                    val recommandations = engine.recommendFor(wallet)
                    call.respond(recommandations)
                } catch (error: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, error.localizedMessage)
                }
            }
        }
    }

}
