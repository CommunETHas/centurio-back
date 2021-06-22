package fr.hadaly.core.service

import fr.hadaly.core.model.Cover

interface CoverRepository {
        suspend fun getAllCovers(): List<Cover>

        suspend fun getCover(id: Int): Cover

        suspend fun getCoverByAddress(address: String): Cover

        suspend fun deleteCover(id: Int): Boolean
}
