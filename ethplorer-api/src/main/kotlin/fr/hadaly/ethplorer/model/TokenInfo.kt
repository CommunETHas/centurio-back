package fr.hadaly.ethplorer.model

import fr.hadaly.ethplorer.model.serializer.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

/*
{
    address:             # token address,
    totalSupply:         # total token supply,
    name:                # token name,
    symbol:              # token symbol,
    decimals:            # number of significant digits,
    price: {             # token price (false, if not available)
        rate:            # current rate
        currency:        # token price currency (USD)
        diff:            # 24 hours rate difference (in percent)
        diff7d:          # 7 days rate difference (in percent)
        diff30d:         # 30 days rate difference (in percent)
        marketCapUsd:    # market cap (USD)
        availableSupply: # available supply
        volume24h:       # 24 hours volume
        ts:              # last rate update timestamp
    },
    owner:               # token owner address,
    countOps:            # total count of token operations
    totalIn:             # total amount of incoming tokens
    totalOut:            # total amount of outgoing tokens
    transfersCount:      # total number of token operations
    ethTransfersCount:   # total number of ethereum operations, optional
    holdersCount:        # total numnber of token holders
    issuancesCount:      # total count of token issuances
    image:               # token image url, optional
    description:         # token description, optional
    website:             # token website url, optional
    lastUpdated:         # last update timestamp
}
 */

@Serializable
data class TokenInfo(
    val address: String, // Address
    val coingecko: String? = null,
    val decimals: Int,
    val ethTransfersCount: Int? = null,
    val holdersCount: Int,
    val image: String? = null,
    val issuancesCount: Int,
    val lastUpdated: Long,
    val name: String,
    val symbol: String,
    val owner: String? = null, // Address
    @Serializable(with = BigIntegerSerializer::class)
    val totalSupply: BigInteger
)
