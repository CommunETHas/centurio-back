package fr.hadaly.web

import arrow.core.Either
import fr.hadaly.core.service.NotificationService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.admin(notificationService: NotificationService) {
    authenticate("admin") {
        route("/admin") {
            get("/notify-job") {
                when (val notifyRequest = notificationService.processNotifications()) {
                    is Either.Left -> {
                        call.respond(HttpStatusCode.InternalServerError, notifyRequest.value.message.toString())
                    }
                    is Either.Right -> {
                        call.respond(HttpStatusCode.OK, "Notification job successful.")
                    }
                }
            }
        }
    }
}
