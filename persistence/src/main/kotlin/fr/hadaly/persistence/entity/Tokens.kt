package fr.hadaly.persistence.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

internal object Tokens : IntIdTable() {
    val name = varchar("name", 255)
    val address = varchar("address", 42).uniqueIndex()
    val owner = varchar("owner", 42).nullable()
    val symbol = varchar("symbol", 255)
    val known = bool("known").default(false)
    override val primaryKey = PrimaryKey(id)
}

internal class TokenEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TokenEntity>(Tokens)
    var name by Tokens.name
    var address by Tokens.address
    var owner by Tokens.owner
    var known by Tokens.known
    var symbol by Tokens.symbol
    var recommendedCovers by CoverEntity via TokensCovers
    val underlyingTokens by UnderlyingToken referrersOn UnderlyingTokens.parent
}

internal object UnderlyingTokens: IntIdTable() {
    val parent = reference("parent", Tokens)
    val child = reference("child", Tokens)
}

internal class UnderlyingToken(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<UnderlyingToken>(UnderlyingTokens)
    var parent by UnderlyingTokens.parent
    var child by UnderlyingTokens.child
}

internal object TokensCovers: Table() {
    val token = reference("token", Tokens)
    val cover = reference("cover", Covers)
    val reason = text("reason").nullable()
    override val primaryKey = PrimaryKey(token, cover)
}
