package fr.hadaly.service

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import fr.hadaly.MAX_POOL_SIZE
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.slf4j.LoggerFactory
import javax.sql.DataSource

object DatabaseFactory {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun connectAndMigrate(dbUrl: String? = null) {
        log.info("Initialising database")
        val pool = hikari(dbUrl)
        Database.connect(pool)
        runFlyway(pool)
    }

    private fun hikari(dbUrl: String?): HikariDataSource {
        val config = if (dbUrl.isNullOrBlank()) {
            HikariConfig().apply {
                driverClassName = "org.h2.Driver"
                jdbcUrl = "jdbc:h2:mem:test"
                maximumPoolSize = MAX_POOL_SIZE
                isAutoCommit = false
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                validate()
            }
        } else {
            HikariConfig().apply {
                driverClassName = "org.postgresql.Driver"
                jdbcUrl = dbUrl
                transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                validate()
            }
        }
        return HikariDataSource(config)
    }

    private fun runFlyway(datasource: DataSource) {
        val flyway = Flyway.configure()
            .dataSource(datasource)
            .load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: FlywayException) {
            log.error("Exception running flyway migration", e)
            throw e
        }
        log.info("Flyway migration has finished")
    }

    suspend fun <T> dbQuery(
        block: suspend () -> T
    ): T =
        newSuspendedTransaction { block() }

}
