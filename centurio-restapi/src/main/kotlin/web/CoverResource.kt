package web

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import service.CoverService

@ExperimentalCoroutinesApi
fun Route.cover(coverService: CoverService) {

    route("/widget") {

        get {
            call.respond(coverService.getAllCovers())
        }
    }
}
