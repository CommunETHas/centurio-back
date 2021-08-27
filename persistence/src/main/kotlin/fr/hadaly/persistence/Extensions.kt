package fr.hadaly.persistence

import fr.hadaly.core.model.Chain
import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.CoverType
import fr.hadaly.core.model.ResourceUrl
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.model.User
import fr.hadaly.core.toSupportedChain
import fr.hadaly.persistence.entity.CoverEntity
import fr.hadaly.persistence.entity.TokenEntity
import fr.hadaly.persistence.entity.UserEntity

internal fun TokenEntity.toToken(): SimpleToken =
    SimpleToken(
        name = name,
        address = address,
        symbol = symbol,
        owner = owner,
        known = known,
        recommendedCovers = recommendedCovers.map { it.toCover() },
        underlyingTokens = underlyingTokens.map { it.toToken() },
        logoUrl = ResourceUrl("/asset/$address.png")
    )


internal fun CoverEntity.toCover() =
    Cover(
        name = name,
        address = address,
        type = CoverType.valueOf(type),
        logo = logo,
        supportedChains = supportedChains.map {
            Chain.valueOf(it.name).toSupportedChain()
        }
    )

internal fun UserEntity.toUser() =
    User(
        address = address,
        email = email,
        nonce = nonce,
        frequency = frequency
    )
