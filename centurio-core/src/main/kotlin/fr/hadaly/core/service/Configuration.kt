package fr.hadaly.core.service

interface Configuration {
    fun getString(key: String): String
    fun getString(name: Name): String

    enum class Name(val key: String) {
        ETHEREUM_CHAIN("ktor.deployment.network"),
        STORAGE_URL("ktor.deployment.storage"),
        ETHPLORER_APIKEY("ktor.deployment.ethplorer"),
        MAIL_BASE_URL("ktor.deployment.sendmail")
    }
}
