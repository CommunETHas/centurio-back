package fr.hadaly.module

import fr.hadaly.isDev
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*

fun Application.corsModule() {
    install(CORS) {
        if (isDev) {
            anyHost()
            method(HttpMethod.Post)
            method(HttpMethod.Put)
            header(HttpHeaders.Authorization)
            header(HttpHeaders.AccessControlAllowCredentials)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowNonSimpleContentTypes = true
            allowCredentials = true
        } else {
            val frontHost = environment.config.property("ktor.deployment.front_host").getString()
            host(frontHost, schemes = listOf("https"))
            method(HttpMethod.Post)
            method(HttpMethod.Put)
            header(HttpHeaders.Authorization)
            header(HttpHeaders.AccessControlAllowCredentials)
            header(HttpHeaders.AccessControlAllowOrigin)
            allowNonSimpleContentTypes = true
            allowCredentials = true
        }
    }
}
