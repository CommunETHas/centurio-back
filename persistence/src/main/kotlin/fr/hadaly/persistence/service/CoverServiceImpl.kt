package fr.hadaly.persistence.service

import arrow.core.Either
import fr.hadaly.persistence.entity.ChainEntity
import fr.hadaly.persistence.entity.ChainsCovers
import fr.hadaly.nexusapi.NexusMutalService
import fr.hadaly.persistence.entity.CoverEntity
import fr.hadaly.persistence.entity.Covers
import fr.hadaly.core.model.Cover
import fr.hadaly.core.service.CoverRepository
import fr.hadaly.nexusapi.model.Chain
import fr.hadaly.nexusapi.model.CoverInfo
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toCover
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CoverServiceImpl : CoverRepository, KoinComponent {

    init {
        initCoversData()
    }

    private fun initCoversData() {
        CoroutineScope(Dispatchers.IO).launch {
            if (getAllCovers().isEmpty()) {
                val nexusMutualServiceImpl: NexusMutalService = get()
                val contracts = nexusMutualServiceImpl.getCoverContracts()
                val chainEntities = insertAllChainEntities()
                val coverEntities = insertAllCoverEntities(contracts)
                insertAllChainsCovers(coverEntities, chainEntities)
            }
        }
    }

    private fun insertAllChainsCovers(
        coverEntities: List<Pair<CoverEntity, List<Chain>>>,
        chainEntities: Map<Chain, ChainEntity>
    ) {
        transaction {
            coverEntities.forEach { (coverEntity, chains) ->
                chains.forEach { chainEntity ->
                    ChainsCovers.insert {
                        it[cover] = coverEntity.id
                        it[chain] = chainEntities[chainEntity]!!.id
                    }
                }
            }
        }
    }

    private fun insertAllChainEntities() = transaction {
        val chainEntities = Chain.values().associate { chain ->
            chain to ChainEntity.new {
                name = chain.name
            }
        }
        chainEntities
    }

    private fun insertAllCoverEntities(contracts: Map<String, CoverInfo>) =
        transaction {
            contracts.filter { !it.value.deprecated }.map { cover ->
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
                } to cover.value.supportedChains
            }
        }

    override suspend fun getAllCovers(): List<Cover> = dbQuery {
        CoverEntity.all().map { it.toCover() }
    }

    override suspend fun getCover(id: Int): Cover = dbQuery {
        CoverEntity[id].toCover()
    }

    override suspend fun getCoverByAddress(address: String): Either<Throwable, Cover> = dbQuery {
        Either.catch {
            CoverEntity.find { Covers.address eq address }.first().toCover()
        }
    }

    override suspend fun deleteCover(id: Int): Boolean {
        return dbQuery {
            Covers.deleteWhere { Covers.id eq id } > 0
        }
    }
}
