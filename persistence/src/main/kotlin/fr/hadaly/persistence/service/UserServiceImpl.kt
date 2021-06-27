package fr.hadaly.persistence.service

import arrow.core.Either
import fr.hadaly.core.model.User
import fr.hadaly.core.service.UserRepository
import fr.hadaly.persistence.entity.UserEntity
import fr.hadaly.persistence.entity.Users
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toUser

class UserServiceImpl: UserRepository {
    override suspend fun getUser(address: String): Either<Throwable, User> = dbQuery {
        Either.catch { UserEntity.find { Users.address eq address }.first().toUser() }
    }

    override suspend fun addUser(user: User): Either<Throwable, User> = dbQuery {
        Either.catch { UserEntity.new {
            address = user.address
            email = user.email
            nonce = user.nonce
        }.toUser() }
    }

    override suspend fun updateUser(user: User): Either<Throwable, User> = dbQuery {
        Either.catch {
            UserEntity.find { Users.address eq user.address }.first().apply {
                address = user.address
                email = user.email
                nonce = user.nonce
            }.toUser()
        }
    }
}
