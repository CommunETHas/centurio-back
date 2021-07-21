package fr.hadaly.persistence.utils

import fr.hadaly.core.model.Chain
import fr.hadaly.persistence.entity.ChainEntity
import fr.hadaly.persistence.entity.ChainsCovers
import fr.hadaly.persistence.entity.CoverEntity
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object CoverInitialization {

    fun insertAllChainsCovers(
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

//    fun insertAllCoverEntities(contracts: Map<String, CoverInfo>) =
//        transaction {
//            contracts.filter { !it.value.deprecated }.map { cover ->
//                CoverEntity.new {
//                    name = cover.value.name
//                    address = cover.key
//                    type = cover.value.type.name
//                    site = cover.value.site
//                    symbol = cover.value.symbol
//                    underlyingToken = cover.value.underlyingToken
//                    dateAdded = cover.value.dateAdded.time
//                    deprecated = cover.value.deprecated
//                    logo = cover.value.logo
//                } to cover.value.supportedChains
//            }
//        }
}
