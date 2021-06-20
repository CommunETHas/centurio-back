package fr.hadaly.web

import RecommandationEngine
import fr.hadaly.service.CoverService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.util.*

@ExperimentalCoroutinesApi
fun Route.cover(coverService: CoverService, engine: RecommandationEngine) {

    route("/cover") {

        get {
            call.respond(coverService.getAllCovers())
        }

        get("/recommend/{wallet}") {
            if (call.parameters.contains("wallet")) {
                val wallet = call.parameters.getOrFail("wallet")
                engine.recommendFor(wallet)
                call.respond("Received address is $wallet")
            }
        }
    }

}
