package fr.hadaly.persistence.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

internal object Chains : IntIdTable() {
    val name = varchar("name", 255)
}

internal class ChainEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ChainEntity>(Chains)
    var name by Chains.name
}
