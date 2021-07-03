package fr.hadaly.core.service

import arrow.core.Option

interface Configuration {
    fun getString(key: String): String
}
