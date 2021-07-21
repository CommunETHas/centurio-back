package fr.hadaly.core.repository

import arrow.core.Either
import fr.hadaly.core.datasource.LocalDataSource
import fr.hadaly.core.datasource.RemoteDataSource
import fr.hadaly.core.model.Cover
import fr.hadaly.core.service.CoverRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoverRepositoryImpl(
    private val localDataSource: LocalDataSource<Cover, Int>,
    private val remoteDataSource: RemoteDataSource<Cover, String>
) : CoverRepository {

    init {
        initCoversData()
    }

    private fun initCoversData() {
        CoroutineScope(Dispatchers.IO).launch {
            if (getAllCovers().isEmpty()) {
                val contracts = remoteDataSource.getAll()
                localDataSource.addAll(contracts)
            }
        }
    }

    override suspend fun getAllCovers(): List<Cover> =
        localDataSource.getAll()

    override suspend fun getCover(id: Int): Either<Throwable, Cover> =
        localDataSource.getById(id)

    override suspend fun getCoverByAddress(address: String): Either<Throwable, Cover> {
        return localDataSource.getByKey("address" to address)
    }

    override suspend fun deleteCover(id: Int): Boolean {
        return localDataSource.delete(id)
    }
}
