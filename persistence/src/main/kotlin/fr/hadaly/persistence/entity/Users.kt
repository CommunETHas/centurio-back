package fr.hadaly.persistence.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable() {
    val address = varchar("address", 42).uniqueIndex()
    val email = varchar("email", 255)
    val nonce = varchar("nonce", 255)
}

class UserEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)
    var email by Users.email
    var address by Users.address
    var nonce by Users.nonce
}
