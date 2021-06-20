package fr.hadaly.service

import fr.hadaly.entity.CoverEntity
import fr.hadaly.entity.Covers
import fr.hadaly.model.*
import fr.hadaly.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class CoverService {

    suspend fun getAllCovers(): List<Cover> = dbQuery {
        CoverEntity.all().map { it.toCover() }
    }

    suspend fun getCover(id: Int): CoverEntity = dbQuery {
        CoverEntity[id]
    }

    suspend fun deleteCover(id: Int): Boolean {
        return dbQuery {
            Covers.deleteWhere { Covers.id eq id } > 0
        }
    }

    private fun CoverEntity.toCover() =
        Cover(
            name = name,
            type = CoverType.valueOf(type),
            dateAdded = dateAdded.toString(),
            supportedChains = supportedChains.map { Chain.valueOf(it.name) }
        )
}
