package fr.hadaly.nexusapi

import fr.hadaly.nexusapi.model.Cover

interface NexusMutalService {
    suspend fun getCoverContracts() : Map<String, Cover>
}
