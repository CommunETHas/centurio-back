package fr.hadaly.engine

import arrow.core.Either
import arrow.core.getOrHandle
import fr.hadaly.core.model.Cover
import fr.hadaly.core.model.Reasoning
import fr.hadaly.core.model.Recommandation
import fr.hadaly.core.model.Recommandations
import fr.hadaly.core.model.ResourceUrl
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.model.Wallet
import fr.hadaly.core.repository.TokenRepository
import fr.hadaly.core.service.RecommendationEngine
import fr.hadaly.core.service.WalletService
import org.slf4j.LoggerFactory

class RecommendationEngineImpl(
    private val walletService: WalletService,
    private val tokenRepository: TokenRepository
) : RecommendationEngine {
    private val logger = LoggerFactory.getLogger(RecommendationEngineImpl::class.java)

    override suspend fun recommendFor(address: String): Either<Throwable, Recommandations> {
        return when (val wallet = walletService.getWallet(address)) {
            is Either.Left -> {
                logger.error("Wallet $address is not a valid wallet : ${wallet.value.message}")
                Either.Left(IllegalArgumentException("Address $address is not valid."))
            }
            is Either.Right -> {
                logger.info(
                    "Checking recommandation for $address " +
                            "with ${wallet.value.transactionCount} transactions."
                )
                Either.Right(processRawTokens(wallet.value))
            }
        }
    }

    private suspend fun processRawTokens(wallet: Wallet): Recommandations {
        val splitTokens = splitSupportedTokens(wallet.tokens)
        val supportedTokens = splitTokens.getOrDefault(false, emptyList())
        val unsupportedTokens = splitTokens.getOrDefault(true, emptyList())
        val recommendations = processSupportedTokens(supportedTokens)
        return Recommandations(
                recommendations.size,
                recommandations = recommendations,
                unsuportedTokens = unsupportedTokens
            )
    }

    private fun processSupportedTokens(tokens: List<SimpleToken>): List<Recommandation> {
        val coverToReasoning = mutableMapOf<String, MutableList<Reasoning>>()
        val coverIdtoCover = mutableMapOf<String, Cover>()
        tokens.forEach { token -> handleCover(coverToReasoning, coverIdtoCover, token) }
        return coverToReasoning.map { Recommandation(coverIdtoCover[it.key]!!, it.value.toList()) }
    }

    private fun handleCover(
        coverToReasoning: MutableMap<String, MutableList<Reasoning>>,
        coverIdToCover: MutableMap<String, Cover>,
        token: SimpleToken
    ) {
        token.recommendedCovers.forEach { cover ->
            if (!coverToReasoning.containsKey(cover.address)) coverToReasoning[cover.address] = mutableListOf()
            coverToReasoning[cover.address]?.add(getReasoning(token))
            coverIdToCover[cover.address] = cover
        }
    }

    private fun getReasoning(token: SimpleToken) =
        Reasoning(
            token.symbol,
            ResourceUrl(token.logoUrl.value),
            "Lorem ipsum my friend, Lorem Ipsum !"
        )

    private suspend fun splitSupportedTokens(tokens: List<SimpleToken>): Map<Boolean, List<SimpleToken>> =
        tokens.mapNotNull { token ->
            tokenRepository.getTokenByAddress(token.address).getOrHandle {
                logger.error("Failed to check token : ${it.message}")
                null
            }
        }.groupBy { it.recommendedCovers.isEmpty() }
}
