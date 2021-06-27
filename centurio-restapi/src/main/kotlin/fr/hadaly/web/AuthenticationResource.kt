package fr.hadaly.web

import arrow.core.Either
import fr.hadaly.core.model.User
import fr.hadaly.core.model.toPublicUser
import fr.hadaly.core.service.UserRepository
import fr.hadaly.model.AuthenticationRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun Route.authentication(userRepository: UserRepository) {

    route("/authentication") {

        post("/") {
            val authRequest = call.receive<AuthenticationRequest>()
        }
    }
}
