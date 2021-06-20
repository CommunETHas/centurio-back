package fr.hadaly.service

import fr.hadaly.nexusapi.NexusMutalService
import fr.hadaly.entity.CoverEntity
import fr.hadaly.entity.Covers
import fr.hadaly.model.Cover
import fr.hadaly.nexusapi.model.Chain
import fr.hadaly.nexusapi.model.CoverType
import fr.hadaly.service.DatabaseFactory.dbQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CoverServiceImpl : CoverService, KoinComponent {

    init {
        initCoversData()
    }

    private fun initCoversData() {
        CoroutineScope(Dispatchers.IO).launch {
            if (getAllCovers().isEmpty()) {
                val nexusMutualServiceImpl: NexusMutalService = get()
                val contracts = nexusMutualServiceImpl.getCoverContracts()
                contracts.filter { !it.value.deprecated }.forEach { cover ->
                    transaction {
                        CoverEntity.new {
                            name = cover.value.name
                            address = cover.key
                            type = cover.value.type.name
                            site = cover.value.site
                            symbol = cover.value.symbol
                            underlyingToken = cover.value.underlyingToken
                            dateAdded = cover.value.dateAdded.time
                            deprecated = cover.value.deprecated
                            logo = cover.value.logo
                        }
                    }
                }
            }
        }
    }

    override suspend fun getAllCovers(): List<Cover> = dbQuery {
        CoverEntity.all().map { it.toCover() }
    }

    override suspend fun getCover(id: Int): CoverEntity = dbQuery {
        CoverEntity[id]
    }

    override suspend fun deleteCover(id: Int): Boolean {
        return dbQuery {
            Covers.deleteWhere { Covers.id eq id } > 0
        }
    }

    private fun CoverEntity.toCover() =
        Cover(
            name = name,
            address = address,
            type = CoverType.valueOf(type),
            supportedChains = supportedChains.map { Chain.valueOf(it.name) }
        )
}
