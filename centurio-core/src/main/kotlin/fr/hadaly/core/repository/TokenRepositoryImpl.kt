package fr.hadaly.core.repository

import arrow.core.Either
import fr.hadaly.core.datasource.LocalDataSource
import fr.hadaly.core.datasource.RemoteDataSource
import fr.hadaly.core.model.SimpleToken

class TokenRepositoryImpl(
    val localDataSource: LocalDataSource<SimpleToken, String>,
    val remoteDataSource: RemoteDataSource<SimpleToken, String>
) : TokenRepository {

    override suspend fun getTokens(): List<SimpleToken> =
        localDataSource.getAll()

    override suspend fun getTokenByAddress(address: String): Either<Throwable, SimpleToken> {
        return when (val token = localDataSource.getByKey("address" to address)) {
            is Either.Left -> {
                when (val remoteToken = remoteDataSource.getById(address)) {
                    is Either.Left -> remoteToken
                    is Either.Right -> {
                        addToken(remoteToken.value)
                        remoteToken
                    }
                }
            }
            is Either.Right -> token
        }
    }

    override suspend fun addToken(token: SimpleToken) {
        localDataSource.add(token)
    }

    override suspend fun updateToken(token: SimpleToken) {
        localDataSource.update(token)
    }

    override suspend fun addTokens(tokens: Iterable<SimpleToken>) {
        localDataSource.addAll(tokens)
    }

    override suspend fun updateTokens(tokens: Iterable<SimpleToken>) {
        localDataSource.updateAll(tokens)
    }

    override suspend fun delete(token: String): Boolean {
        return localDataSource.delete(token)
    }
}
