package fr.hadaly.web

import arrow.core.Either
import fr.hadaly.core.model.User
import fr.hadaly.core.model.toPublicUser
import fr.hadaly.core.service.UserRepository
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.auth.*

fun Route.user(userRepository: UserRepository) {

    route("/user") {

        get("/{address}") {
            val address = call.parameters.getOrFail("wallet")
            when (val user = userRepository.getUser(address)) {
                is Either.Left -> {
                    call.respond(HttpStatusCode.NotFound, "User not registered.")
                }
                is Either.Right -> {
                    call.respond(user.value.toPublicUser())
                }
            }
        }

        post("/{address}") {
            val address = call.parameters.getOrFail("wallet")
            userRepository.addUser(User(address, nonce = generateNonce()))
        }
    }
}
