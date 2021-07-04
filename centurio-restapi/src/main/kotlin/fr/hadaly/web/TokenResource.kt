package fr.hadaly.web

import ch.qos.logback.core.subst.Token
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.CoverRepository
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.engine.toSimpleToken
import fr.hadaly.ethplorer.EthplorerService
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
            post {
                val tokenRequest = call.receive<List<TokenRequest>>()
                call.application.environment.log.info(tokenRequest.toString())
                tokenRequestHandler.handle(tokenRequest)
                call.respond(HttpStatusCode.Accepted, "Saved cover recommandations")
            }
        }
    }
}

