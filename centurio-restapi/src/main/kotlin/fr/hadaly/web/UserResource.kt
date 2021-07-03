package fr.hadaly.web

import arrow.core.Either
import fr.hadaly.core.model.User
import fr.hadaly.core.model.toPublicUser
import fr.hadaly.core.service.UserRepository
import fr.hadaly.model.ErrorResponse
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
                    val response = ErrorResponse(HttpStatusCode.NotFound.value, "User not registered.")
                    call.respond(HttpStatusCode.NotFound, response)
                }
                is Either.Right -> {
                    handleKnownUser(userRepository, user)
                }
            }
        }

        authenticate {
            get("/private/{address}") {
                val address = call.parameters.getOrFail("address")
                when (val user = userRepository.getUser(address)) {
                    is Either.Left -> {
                        val response = ErrorResponse(HttpStatusCode.NotFound.value, "User not registered.")
                        call.respond(HttpStatusCode.NotFound, response)
                    }
                    is Either.Right -> {
                        handleKnownUser(userRepository, user, false)
                    }
                }
            }

            put {
                val user = call.receive<User>()
                application.environment.log.info("Received user $user")
                when (val user = userRepository.updateUser(user)) {
                    is Either.Left -> {
                        val response =
                            ErrorResponse(HttpStatusCode.InternalServerError.value, user.value.message.toString())
                        call.respond(HttpStatusCode.InternalServerError, response)
                    }
                    is Either.Right -> {
                        call.respond(HttpStatusCode.Accepted, "User updated")
                    }
                }
            }
        }

        post("/{address}") {
            val address = call.parameters.getOrFail("address")
            when (val user = userRepository.addUser(User(address))) {
                is Either.Left -> {
                    val response =
                        ErrorResponse(HttpStatusCode.InternalServerError.value, user.value.message.toString())
                    call.respond(HttpStatusCode.InternalServerError, response)
                }
                is Either.Right -> {
                    call.respond(HttpStatusCode.Created, "User registered")
                }
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleKnownUser(
    userRepository: UserRepository,
    user: Either.Right<User>,
    public: Boolean = true
) {
    when (val refreshedUser = userRepository.updateUser(user.value.copy(nonce = generateNonce()))) {
        is Either.Left -> {
            call.respond(HttpStatusCode.InternalServerError, refreshedUser.value.message.toString())
        }
        is Either.Right -> {
            application.environment.log.info(refreshedUser.value.toString())
            if (public) {
                call.respond(refreshedUser.value.toPublicUser())
            } else {
                call.respond(refreshedUser.value)
            }
        }
    }
}
