package fr.hadaly.web

import arrow.core.Either
import fr.hadaly.core.model.User
import fr.hadaly.core.model.toPublicUser
import fr.hadaly.core.service.UserRepository
import fr.hadaly.handler.AuthenticationRequestHandler
import fr.hadaly.model.AccessToken
import fr.hadaly.model.AuthenticationRequest
import fr.hadaly.util.JwtConfig
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun Route.authentication(authenticationRequestHandler: AuthenticationRequestHandler, jwtConfig: JwtConfig) {

    route("/authentication") {

        post {
            val authRequest = call.receive<AuthenticationRequest>()
            application.environment.log.info(authRequest.signature)
            when(val verification = authenticationRequestHandler.verify(authRequest.user, authRequest.signature)) {
                is Either.Left -> {
                    call.respond(HttpStatusCode.Unauthorized, "Authentication failed : ${verification.value.message}")
                }
                is Either.Right -> {
                    if (verification.value) {
                        call.respond(AccessToken(jwtConfig.makeToken(authRequest.user.address)))
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, "Authentication failed")
                    }
                }
            }
        }
    }
}
