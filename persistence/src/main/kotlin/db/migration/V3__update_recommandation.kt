package db.migration

import fr.hadaly.persistence.entity.Tokens
import fr.hadaly.persistence.entity.TokensCovers
import fr.hadaly.persistence.entity.UnderlyingTokens
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class V3__update_recommandation: BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(TokensCovers)
            SchemaUtils.create(UnderlyingTokens)
            SchemaUtils.createMissingTablesAndColumns(Tokens)
        }
    }
}
