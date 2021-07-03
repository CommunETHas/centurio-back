package fr.hadaly.persistence.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object Users: IntIdTable() {
    val address = varchar("address", 42).uniqueIndex()
    val email = varchar("email", 255).nullable()
    val nonce = varchar("nonce", 255)
    val frequency = varchar("frequency", 32).default("never")
    val notifiedAt = datetime("notifiedAt").nullable()
}

class UserEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)
    var email by Users.email
    var address by Users.address
    var nonce by Users.nonce
    var frequency by Users.frequency
    var notifiedAt by Users.notifiedAt
}
