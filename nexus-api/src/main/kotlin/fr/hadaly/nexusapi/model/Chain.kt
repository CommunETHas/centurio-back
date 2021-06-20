package fr.hadaly.nexusapi.model

import kotlinx.serialization.Serializable
import fr.hadaly.nexusapi.model.serializer.ChainSerializer

@Serializable(with = ChainSerializer::class)
enum class Chain {
    ETHEREUM,
    BSC,
    FANTOM,
    POLYGON,
    STARKWARE,
    XDAI,
    TERRA,
    THORCHAIN
}
