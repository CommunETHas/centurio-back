package fr.hadaly.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.config.*
import java.util.*

private const val DAYS = 30L
private const val HOURS = 24L
private const val MS_PER_HOUR = 36_000_00L

class JwtConfig(config: ApplicationConfig) {

    private val validityInMs = MS_PER_HOUR * HOURS * DAYS
    private val secret: String = "pure-escarpment-71696-centurio" ?: config.property("ktor.jwt.secret").getString()
    private val issuer: String = config.property("ktor.jwt.issuer").getString()
    private val audience: String = config.property("ktor.jwt.audience").getString()

    private val algorithm = Algorithm.HMAC512(secret)

    val userVerifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withClaimPresence("user")
        .build()

    val adminVerifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withClaimPresence("admin")
        .build()

    /**
     * Produce a token for an Account
     */
    fun makeToken(login: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("user", login)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    /**
     * Produce an Api token
     */
    fun makeApiToken(login: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience("api$audience")
        .withClaim("admin", login)
        .sign(algorithm)

    fun isApiToken(audience: List<String>) = "api${this.audience}" in audience

    fun isUserToken(audience: List<String>) = !isApiToken(audience)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
