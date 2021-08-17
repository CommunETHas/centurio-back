package fr.hadaly.core.model

import fr.hadaly.core.model.serializer.ChainSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ChainSerializer::class)
enum class Chain {
    BSC,
    ETHEREUM,
    FANTOM,
    OPTIMISM,
    POLYGON,
    STARKWARE,
    TERRA,
    THORCHAIN,
    XDAI,
}
