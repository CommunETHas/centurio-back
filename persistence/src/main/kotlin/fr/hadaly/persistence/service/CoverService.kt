package fr.hadaly.persistence.service

import fr.hadaly.persistence.entity.CoverEntity
import fr.hadaly.core.model.Cover

interface CoverService {

    suspend fun getAllCovers(): List<CoverEntity>

    suspend fun getCover(id: Int): CoverEntity

    suspend fun deleteCover(id: Int): Boolean
}

