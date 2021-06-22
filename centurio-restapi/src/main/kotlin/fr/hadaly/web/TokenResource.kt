package fr.hadaly.web

import fr.hadaly.core.service.CoverRepository
import fr.hadaly.core.service.TokenRepository
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.routing.*
import fr.hadaly.model.TokenRequest

fun Route.token(tokenRepository: TokenRepository, coverRepository: CoverRepository) {

    route("/token") {

        post {
            val tokenRequest = call.receive<TokenRequest>()
            call.application.environment.log.info(tokenRequest.toString())

            val token = tokenRepository.getTokenByAddress(tokenRequest.address)
            val covers = tokenRequest.covers.map {
                coverRepository.getCoverByAddress(it)
            }
            val updatedToken = token.copy(recommendedCovers = covers)
            tokenRepository.updateToken(updatedToken)
        }

    }

}
