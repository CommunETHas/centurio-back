package fr.hadaly.persistence.service

import arrow.core.Either
import fr.hadaly.core.datasource.LocalDataSource
import fr.hadaly.core.model.Cover
import fr.hadaly.persistence.entity.ChainEntity
import fr.hadaly.persistence.entity.Chains
import fr.hadaly.persistence.entity.CoverEntity
import fr.hadaly.persistence.entity.Covers
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toCover
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.lowerCase

class CoverLocalDataSource : LocalDataSource<Cover, Int> {

    override suspend fun getAll(): List<Cover> = dbQuery {
        CoverEntity.all().map { it.toCover() }
    }

    override suspend fun getById(id: Int): Either<Throwable, Cover> = dbQuery {
        Either.catch { CoverEntity[id].toCover() }
    }

    override suspend fun delete(id: Int): Boolean =
        dbQuery { Covers.deleteWhere { Covers.id eq id } > 0 }

    override suspend fun getByKey(key: Pair<String, String>): Either<Throwable, Cover> =
        Either.catch {
            when (key.first) {
                "address" -> getCoverByAddress(key.second)
                else -> throw NoSuchElementException("Unsupported key ${key.second}")
            }
        }

    override suspend fun add(item: Cover) {
        dbQuery { addCover(item) }
    }

    override suspend fun addAll(items: Iterable<Cover>) {
        dbQuery { items.forEach { cover -> addCover(cover) } }
    }

    override suspend fun update(item: Cover) {
        dbQuery { updateCover(item) }
    }

    override suspend fun updateAll(items: Iterable<Cover>) = dbQuery {
        items.forEach { cover -> updateCover(cover) }
    }

    private suspend fun getCoverByAddress(address: String): Cover = dbQuery {
        CoverEntity.find {
            Covers.address.lowerCase() eq address.lowercase()
        }.first().toCover()
    }

    private fun addCover(item: Cover) {
        val (coverEntity, chains) = CoverEntity.new {
            name = item.name
            address = item.address
            type = item.type.name
            logo = item.logo
        } to item.supportedChains

        coverEntity.apply {
            supportedChains = ChainEntity.find {
                Chains.name inList chains.map { it.name.toString() }
            }
        }
    }

    private fun updateCover(item: Cover) {
        CoverEntity.find {
            Covers.address.lowerCase() eq item.address.lowercase()
        }.firstOrNull()?.apply {
            address = item.address
            name = item.name
            type = item.type.name
            logo = item.logo
            supportedChains = ChainEntity.find {
                Chains.name inList item.supportedChains.map { it.name.toString() }
            }
        }
    }
}
