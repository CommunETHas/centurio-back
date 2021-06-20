package fr.hadaly.web

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import fr.hadaly.service.CoverService

@ExperimentalCoroutinesApi
fun Route.cover(coverService: CoverService) {

    route("/cover") {

        get {
            call.respond(coverService.getAllCovers())
        }
    }
}
