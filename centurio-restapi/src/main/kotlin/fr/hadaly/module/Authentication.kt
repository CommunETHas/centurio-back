package fr.hadaly.module

import fr.hadaly.util.JwtConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.authenticationModule(jwtConfig: JwtConfig) {
    install(Authentication) {
        jwt("user") {
            realm = environment.config.property("ktor.jwt.realm").getString()
            verifier(jwtConfig.userVerifier)
            validate { credentials ->
                if (jwtConfig.isUserToken(credentials.audience)) {
                    JWTPrincipal(credentials.payload)
                } else {
                    null
                }
            }
        }
        jwt("admin") {
            realm = environment.config.property("ktor.jwt.realm").getString()
            verifier(jwtConfig.adminVerifier)
            validate { credentials ->
                if (jwtConfig.isApiToken(credentials.audience)) {
                    JWTPrincipal(credentials.payload)
                } else {
                    null
                }
            }
        }
    }
}

