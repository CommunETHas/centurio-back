package fr.hadaly.core.datasource

import arrow.core.Either

interface LocalDataSource<T, ID> {
    suspend fun getAll(): List<T>

    suspend fun getById(id: ID): Either<Throwable, T>

    suspend fun getByKey(key: Pair<String, String>): Either<Throwable, T>

    suspend fun delete(id: ID): Boolean

    suspend fun add(item: T)

    suspend fun addAll(items: Iterable<T>)

    suspend fun update(item: T)

    suspend fun updateAll(items: Iterable<T>)
}

