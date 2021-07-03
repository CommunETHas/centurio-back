package fr.hadaly.web

import fr.hadaly.core.service.NotificationService
import io.ktor.auth.*
import io.ktor.routing.*

fun Route.admin(notificationService: NotificationService) {
    authenticate {
        route("/admin") {
            get("/notify-job") {
                notificationService.processNotifications()
            }
        }
    }
}
