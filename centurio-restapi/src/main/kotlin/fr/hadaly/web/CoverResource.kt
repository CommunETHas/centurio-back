package fr.hadaly.web

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import fr.hadaly.service.CoverService
import io.ktor.client.features.*
import io.ktor.http.*

@ExperimentalCoroutinesApi
fun Route.cover(coverService: CoverService) {

    route("/cover") {

        get {
            call.respond(coverService.getAllCovers())
        }

        post("/recommend/{wallet}") {
            if (call.parameters.contains("wallet")) {
                val wallet = call.parameters["wallet"]
                call.respond("Received address is $wallet")
            }
        }
    }
}
