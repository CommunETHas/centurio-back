package fr.hadaly

import fr.hadaly.model.Cover
import org.web3j.abi.datatypes.Address

interface NexusMutalService {
    suspend fun getCoverContracts() : Map<String, Cover>
}
