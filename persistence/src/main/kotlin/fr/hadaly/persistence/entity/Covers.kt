package fr.hadaly.persistence.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Covers : IntIdTable() {
    val name = varchar("name", 255)
    val address = varchar("address", 255).uniqueIndex()
    val type = varchar("type", 32)
    val site = varchar("site", 255).nullable()
    val symbol = varchar("symbol", 255).nullable()
    val underlyingToken = varchar("underlyingToken", 255).nullable()
    val deprecated = bool("deprecated").nullable()
    val logo = varchar("logo", 255).nullable()
    override val primaryKey = PrimaryKey(id)
}

class CoverEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CoverEntity>(Covers)

    var name by Covers.name
    var address by Covers.address
    var type by Covers.type
    var site by Covers.site
    var symbol by Covers.symbol
    var underlyingToken by Covers.underlyingToken
    var deprecated by Covers.deprecated
    var logo by Covers.logo
    var supportedChains by ChainEntity via ChainsCovers
}

object ChainsCovers: Table() {
    val chain = reference("chain", Chains)
    val cover = reference("cover", Covers)
    override val primaryKey = PrimaryKey(chain, cover)
}
