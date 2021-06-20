package fr.hadaly.service

import fr.hadaly.entity.CoverEntity
import fr.hadaly.model.Cover

interface CoverService {

    suspend fun getAllCovers(): List<Cover>

    suspend fun getCover(id: Int): CoverEntity

    suspend fun deleteCover(id: Int): Boolean
}

