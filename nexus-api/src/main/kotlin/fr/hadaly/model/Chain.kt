package fr.hadaly.model

import kotlinx.serialization.Serializable
import fr.hadaly.model.serializer.ChainSerializer

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
