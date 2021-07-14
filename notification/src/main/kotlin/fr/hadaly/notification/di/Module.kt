package fr.hadaly.notification.di

import fr.hadaly.core.service.NotificationService
import fr.hadaly.notification.service.NotificationServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val notificationModule = module {
    factory<NotificationService> {
        NotificationServiceImpl(
            config = get(),
            userRepository = get(),
            recommendationEngine = get()
        )
    }
}

