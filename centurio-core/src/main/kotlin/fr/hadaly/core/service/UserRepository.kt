package fr.hadaly.core.service

import arrow.core.Either
import fr.hadaly.core.model.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUser(address: String): Either<Throwable, User>
    suspend fun addUser(user: User): Either<Throwable, User>
    suspend fun updateUser(user: User): Either<Throwable, User>
}
