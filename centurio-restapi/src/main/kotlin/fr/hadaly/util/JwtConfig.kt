package fr.hadaly.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.config.*
import java.util.*

private const val HOURS = 10
private const val MS_PER_HOUR = 36_000_00

class JwtConfig(config: ApplicationConfig) {

    private val validityInMs = MS_PER_HOUR * HOURS
    private val secret: String = config.property("ktor.jwt.secret").getString()
    private val issuer: String = config.property("ktor.jwt.issuer").getString()

    private val algorithm = Algorithm.HMAC512(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    /**
     * Produce a token for an Account
     */
    fun makeToken(login: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("login", login)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    /**
     * Produce an Api token
     */
    fun makeApiToken(login: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("admin", login)
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
