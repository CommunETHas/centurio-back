package fr.hadaly.nexusapi

import fr.hadaly.nexusapi.model.CoverInfo

interface NexusMutalService {
    suspend fun getCoverContracts() : Map<String, CoverInfo>
}
