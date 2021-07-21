package fr.hadaly.ethplorer

import fr.hadaly.core.model.ResourceUrl
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.ethplorer.model.TokenInfo

fun TokenInfo.toSimpleToken() = SimpleToken(
    name = name,
    address = address,
    owner = owner,
    symbol = symbol,
    logoUrl = ResourceUrl("/asset/$address.png")
)
