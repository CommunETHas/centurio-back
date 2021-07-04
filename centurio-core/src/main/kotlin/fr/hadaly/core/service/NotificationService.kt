package fr.hadaly.core.service

import arrow.core.Either

interface NotificationService {
    suspend fun processNotifications(): Either<Throwable, Unit>
}
