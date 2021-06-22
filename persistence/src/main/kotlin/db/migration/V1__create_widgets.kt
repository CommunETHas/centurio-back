package db.migration

import fr.hadaly.persistence.entity.*
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class V1__create_widgets: BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Chains)
            SchemaUtils.create(Covers)
            SchemaUtils.create(ChainsCovers)
            SchemaUtils.create(Tokens)
            SchemaUtils.create(TokensCovers)
        }
    }
}
