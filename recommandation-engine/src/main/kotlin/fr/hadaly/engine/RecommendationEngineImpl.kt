package fr.hadaly.engine

import arrow.core.Either
import fr.hadaly.core.model.Recommandations
import fr.hadaly.core.model.Recommandation
import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.Reasoning
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.model.ResourceUrl
import fr.hadaly.core.service.RecommendationEngine
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.ethplorer.EthplorerService
import fr.hadaly.ethplorer.model.Token
import fr.hadaly.ethplorer.model.WalletInfo
import org.slf4j.LoggerFactory

class RecommendationEngineImpl(
    private val ethplorerService: EthplorerService,
    private val tokenService: TokenRepository
) : RecommendationEngine {
    private val logger = LoggerFactory.getLogger("fr.hadaly.core.RecommandationEngine")

    override suspend fun recommendFor(address: String): Either<Throwable, Recommandations> {
        val walletInfo: Either<Throwable, WalletInfo> = ethplorerService.getWalletInfo(address)
        return when (walletInfo) {
            is Either.Left -> {
                logger.error(walletInfo.value.message)
                logger.error("Wallet $address is not a valid wallet.")
                Either.Left(IllegalArgumentException("Address $address is not valid."))
            }
            is Either.Right -> {
                logger.info(
                    "Checking recommandation for $address " +
                            "with ${walletInfo.value.transactionCount} transactions."
                )
                val unsupportedTokens =
                    walletInfo.value.tokens.mapNotNull { checkTokens(it) }
                val recommandations =
                    handleTokens((walletInfo.value.tokens - unsupportedTokens))
                println(recommandations)
                Either.Right(Recommandations(
                    recommandations.size,
                    recommandations = recommandations,
                    unsuportedTokens = unsupportedTokens.map { it.toSimpleToken() }
                ))
            }
        }
    }

    suspend fun handleTokens(tokens: List<Token>): List<Recommandation> {
        val coverToReasoning = mutableMapOf<String, MutableList<Reasoning>>()
        val coverIdtoCover = mutableMapOf<String, Cover>()
        tokens.map { tokenService.getTokenByAddress(it.tokenInfo.address) }.forEach {
            when (it) {
                is Either.Left -> {
                    logger.warn("Failed to handle token : ${it.value.message}")
                }
                is Either.Right -> {
                    handleCover(coverToReasoning, coverIdtoCover, it.value)
                }
            }
        }
        return coverToReasoning.map { Recommandation(coverIdtoCover[it.key]!!, it.value.toList()) }
    }

    fun handleCover(
        coverToReasoning: MutableMap<String, MutableList<Reasoning>>,
        coverIdToCover: MutableMap<String, Cover>,
        token: SimpleToken
    ) {
        token.recommendedCovers.forEach { cover ->
            if (!coverToReasoning.containsKey(cover.address)) coverToReasoning[cover.address] = mutableListOf()
            coverToReasoning[cover.address]?.add(buildRecommandation(token))
            coverIdToCover[cover.address] = cover
        }
    }

    private fun buildRecommandation(
        token: SimpleToken
    ) =
        Reasoning(
            token.symbol,
            ResourceUrl(token.logoUrl.value),
            "Lorem ipsum my friend, Lorem Ipsum !"
        )

    suspend fun checkTokens(token: Token): Token? =
        when (val tokenInfoResult = tokenService.getTokenByAddress(token.tokenInfo.address)) {
            is Either.Left -> {
                tokenService.addToken(token.toSimpleToken())
                token
            }
            is Either.Right -> {
                if (tokenInfoResult.value.recommendedCovers.isEmpty()) {
                    token
                } else {
                    null
                }
            }
        }
}
