package fr.hadaly.persistence

import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.nexusapi.model.Chain
import fr.hadaly.nexusapi.model.CoverType
import fr.hadaly.persistence.entity.CoverEntity
import fr.hadaly.persistence.entity.TokenEntity

fun TokenEntity.toToken(): SimpleToken =
    SimpleToken(
        name = name,
        address = address,
        symbol = symbol,
        owner = owner,
        known = known,
        recommendedCovers = recommendedCovers.map { it.toCover() },
        logoUrl = "/$address/logo.png"
    )


fun CoverEntity.toCover() =
    Cover(
        name = name,
        address = address,
        type = CoverType.valueOf(type),
        logo = logo,
        supportedChains = supportedChains.map { Chain.valueOf(it.name) }
    )
