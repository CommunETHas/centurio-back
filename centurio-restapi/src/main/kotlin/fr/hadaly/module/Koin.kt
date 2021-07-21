package fr.hadaly.module

import fr.hadaly.core.di.coreModule
import fr.hadaly.di.restApiModule
import fr.hadaly.engine.di.engineModule
import fr.hadaly.ethplorer.di.ethplorerApiModule
import fr.hadaly.nexusapi.di.nexusApiModule
import fr.hadaly.notification.di.notificationModule
import fr.hadaly.persistence.di.persistenceModule
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.koinModules() {
    install(Koin) {
        slf4jLogger()
        modules(
            module { single { environment.config } },
            coreModule,
            restApiModule,
            nexusApiModule,
            ethplorerApiModule,
            persistenceModule,
            engineModule,
            notificationModule
        )
    }
}
