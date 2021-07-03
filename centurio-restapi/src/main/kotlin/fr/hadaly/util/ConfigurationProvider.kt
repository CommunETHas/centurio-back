package fr.hadaly.util

import fr.hadaly.core.service.Configuration
import io.ktor.config.*

class ConfigurationProvider(private val config: ApplicationConfig): Configuration {
    override fun getString(key: String): String {
        return config.property(key).getString()
    }
}
