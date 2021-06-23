package fr.hadaly.persistence.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Tokens : IntIdTable() {
    val name = varchar("name", 255)
    val address = varchar("address", 42)
    val owner = varchar("owner", 42).nullable()
    val symbol = varchar("symbol", 8)
    val known = bool("known").default(false)
    override val primaryKey = PrimaryKey(id)
}

class TokenEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TokenEntity>(Tokens)
    var name by Tokens.name
    var address by Tokens.address
    var owner by Tokens.owner
    var known by Tokens.known
    var symbol by Tokens.symbol
    var recommendedCovers by CoverEntity via TokensCovers
}

object TokensCovers: Table() {
    val token = reference("token", Tokens)
    val cover = reference("cover", Covers)
    override val primaryKey = PrimaryKey(token, cover)
}
