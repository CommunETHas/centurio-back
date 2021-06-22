package fr.hadaly.engine

import fr.hadaly.core.model.SimpleToken
import fr.hadaly.ethplorer.model.Token
import fr.hadaly.ethplorer.model.TokenInfo
import org.koin.core.qualifier.named

fun Token.toSimpleToken() = SimpleToken(
    name = tokenInfo.name,
    address = tokenInfo.address,
    owner = tokenInfo.owner,
    symbol = tokenInfo.symbol
)

fun TokenInfo.toSimpleToken() = SimpleToken(
    name = name,
    address = address,
    owner = owner,
    symbol = symbol
)
