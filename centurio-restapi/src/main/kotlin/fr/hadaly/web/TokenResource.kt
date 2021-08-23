package fr.hadaly.web

import arrow.core.Either
import ch.qos.logback.core.subst.Token
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.CoverRepository
import fr.hadaly.core.repository.TokenRepository
import fr.hadaly.handler.TokenRequestHandler
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.routing.*
import fr.hadaly.model.TokenRequest
import io.ktor.auth.*

fun Route.token(tokenRequestHandler: TokenRequestHandler) {

    route("/token") {
        authenticate("admin") {
            get {
                call.respond(tokenRequestHandler.getTokens())
            }

            get("/{address}") {
                if (call.parameters.contains("address")) {
                    val address = call.parameters.getOrFail("address")
                    call.application.environment.log.debug("Requesting token $address")
                    when (val token = tokenRequestHandler.getToken(address)) {
                        is Either.Left -> {
                            call.respond(HttpStatusCode.NotFound, "Unknown token address")
                        }
                        is Either.Right -> {
                            call.respond(token.value)
                        }
                    }

                }
            }

            delete("/{address}") {
                if (call.parameters.contains("address")) {
                    val address = call.parameters.getOrFail("address")
                    call.application.environment.log.debug("Requesting token $address")
                    if (tokenRequestHandler.delete(address)) {
                        call.respond(HttpStatusCode.NotFound, "Unknown token address")
                    } else {
                        call.respond(HttpStatusCode.OK)
                    }
                }
            }

            post {
                val tokenRequest = call.receive<List<TokenRequest>>()
                call.application.environment.log.info(tokenRequest.toString())
                tokenRequestHandler.handle(tokenRequest)
                call.respond(HttpStatusCode.Accepted, "Saved cover recommandations")
            }
        }
    }
}

