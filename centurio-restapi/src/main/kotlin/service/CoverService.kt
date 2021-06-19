package service

import fr.hadaly.entity.CoverEntity
import fr.hadaly.entity.Covers
import fr.hadaly.model.*
import fr.hadaly.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class CoverService {

    private val listeners = mutableMapOf<Int, suspend (Notification<CoverEntity?>) -> Unit>()

    fun addChangeListener(id: Int, listener: suspend (Notification<CoverEntity?>) -> Unit) {
        listeners[id] = listener
    }

    fun removeChangeListener(id: Int) = listeners.remove(id)

    private suspend fun onChange(type: ChangeType, id: Int, entity: CoverEntity? = null) {
        listeners.values.forEach {
            it.invoke(Notification(type, id, entity))
        }
    }

    suspend fun getAllCovers(): List<Cover> = dbQuery {
        CoverEntity.all().map { it.toCover() }
    }

    suspend fun getCover(id: Int): CoverEntity = dbQuery {
        CoverEntity[id]
    }

    suspend fun deleteCover(id: Int): Boolean {
        return dbQuery {
            Covers.deleteWhere { Covers.id eq id } > 0
        }.also {
            if (it) onChange(ChangeType.DELETE, id)
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
