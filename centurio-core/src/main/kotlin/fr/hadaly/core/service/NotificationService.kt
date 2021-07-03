package fr.hadaly.core.service

import fr.hadaly.core.model.Recommandations

interface NotificationService {
    suspend fun processNotifications()
}
