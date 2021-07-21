package fr.hadaly.core.datasource

import arrow.core.Either

interface RemoteDataSource<T, ID> {
    suspend fun getAll(): List<T>

    suspend fun getById(id: ID): Either<Throwable, T>
}
