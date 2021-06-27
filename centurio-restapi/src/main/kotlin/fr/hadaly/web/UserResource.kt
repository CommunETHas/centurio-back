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
import io.ktor.util.pipeline.*

fun Route.user(userRepository: UserRepository) {

    route("/user") {

        get("/{address}") {
            val address = call.parameters.getOrFail("address")
            when (val user = userRepository.getUser(address)) {
                is Either.Left -> {
                    call.respond(HttpStatusCode.NotFound, "User not registered.")
                }
                is Either.Right -> {
                    handleKnownUser(userRepository, user)
                }
            }
        }

        post("/{address}") {
            val address = call.parameters.getOrFail("address")
            when (val user = userRepository.addUser(User(address))) {
                is Either.Left -> {
                    call.respond(HttpStatusCode.InternalServerError, user.value.message.toString())
                }
                is Either.Right -> {
                    call.respond(HttpStatusCode.Accepted, "User registered")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleKnownUser(
    userRepository: UserRepository,
    user: Either.Right<User>
) {
    when (val refreshedUser = userRepository.updateUser(user.value.copy(nonce = generateNonce()))) {
        is Either.Left -> {
            call.respond(HttpStatusCode.InternalServerError, refreshedUser.value.message.toString())
        }
        is Either.Right -> {
            call.respond(refreshedUser.value.toPublicUser())
        }
    }
}
