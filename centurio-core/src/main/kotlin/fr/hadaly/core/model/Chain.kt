package fr.hadaly.core.model

import fr.hadaly.core.model.serializer.ChainSerializer
import kotlinx.serialization.Serializable

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
