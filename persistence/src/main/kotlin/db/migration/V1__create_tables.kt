package db.migration

import fr.hadaly.core.model.Chain
import fr.hadaly.persistence.entity.ChainEntity
import fr.hadaly.persistence.entity.Chains
import fr.hadaly.persistence.entity.ChainsCovers
import fr.hadaly.persistence.entity.Covers
import fr.hadaly.persistence.entity.Tokens
import fr.hadaly.persistence.entity.TokensCovers
import fr.hadaly.persistence.entity.Users
import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class V1__create_tables: BaseJavaMigration() {
    override fun migrate(context: Context?) {
        transaction {
            SchemaUtils.create(Chains)
            insertAllChainEntities()
            SchemaUtils.create(Covers)
            SchemaUtils.create(ChainsCovers)
            SchemaUtils.create(Tokens)
            SchemaUtils.create(TokensCovers)
            SchemaUtils.create(Users)
        }
    }

    private fun insertAllChainEntities() {
        val chainEntities = Chain.values().associateWith { chain ->
            ChainEntity.new {
                name = chain.name
            }
        }
        chainEntities
    }
}
