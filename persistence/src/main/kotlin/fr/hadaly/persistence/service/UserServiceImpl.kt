package fr.hadaly.persistence.service

import arrow.core.Either
import fr.hadaly.core.model.User
import fr.hadaly.core.service.UserRepository
import fr.hadaly.persistence.entity.UserEntity
import fr.hadaly.persistence.entity.Users
import fr.hadaly.persistence.service.DatabaseFactory.dbQuery
import fr.hadaly.persistence.toUser
import org.slf4j.LoggerFactory

class UserServiceImpl: UserRepository {
    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override suspend fun getUser(address: String): Either<Throwable, User> = dbQuery {
        logger.info("Adding user $address")
        Either.catch { UserEntity.find { Users.address eq address.lowercase() }.first().toUser() }
    }

    override suspend fun addUser(user: User): Either<Throwable, User> = dbQuery {
        logger.info("Adding user ${user.address}")
        Either.catch { UserEntity.new {
            address = user.address.lowercase()
            email = user.email
            nonce = user.nonce
        }.toUser() }
    }

    override suspend fun updateUser(user: User): Either<Throwable, User> = dbQuery {
        logger.info("Updating user ${user.address}")
        Either.catch {
            UserEntity.find { Users.address eq user.address.lowercase() }.first().apply {
                address = user.address.lowercase()
                email = user.email
                nonce = user.nonce
            }.toUser()
        }
    }
}
