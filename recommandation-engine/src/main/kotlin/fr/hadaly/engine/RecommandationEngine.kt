package fr.hadaly.engine

import arrow.core.Either
import fr.hadaly.core.model.Reasoning
import fr.hadaly.core.model.Recommandation
import fr.hadaly.core.model.Recommandations
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.ethplorer.EthplorerService
import fr.hadaly.ethplorer.model.Token
import fr.hadaly.ethplorer.model.WalletInfo
import org.slf4j.LoggerFactory

class RecommandationEngine(
    private val ethplorerService: EthplorerService,
    private val tokenService: TokenRepository
) {
    private val logger = LoggerFactory.getLogger("fr.hadaly.core.RecommandationEngine")

    suspend fun recommendFor(address: String): Either<Throwable, Recommandations> {
        val walletInfo: Either<Throwable, WalletInfo> = ethplorerService.getWalletInfo(address)
        return when (walletInfo) {
            is Either.Left -> {
                logger.error("Wallet $address is not a valid wallet.")
                Either.Left(IllegalArgumentException("Address $address is not valid."))
            }
            is Either.Right -> {
                logger.info("Checking recommandation for $address " +
                        "with ${walletInfo.value.transactionCount} transactions.")
                val unsupportedTokens = walletInfo.value.tokens.mapNotNull { checkTokens(it) }
                val recommandations = handleTokens((walletInfo.value.tokens - unsupportedTokens))
                Either.Right(Recommandations(
                    recommandations.size,
                    recommandations = recommandations,
                    unsuportedTokens = unsupportedTokens.map { it.toSimpleToken() }
                ))
            }
        }
    }

    suspend fun handleTokens(tokens: List<Token>): List<Recommandation> {
        return tokens.map { tokenService.getTokenByAddress(it.tokenInfo.address) }
            .flatMap { handleCover(it) }.toSet().toList()
    }

    fun handleCover(token: Either<Throwable, SimpleToken>): List<Recommandation> =
        when (token) {
            is Either.Left -> {
                emptyList<Recommandation>()
            }
            is Either.Right -> {
                token.value.recommendedCovers.map { cover ->
                    Recommandation(
                        cover,
                        Reasoning(token.value.symbol, "Lorem ipsum my friend, Lorem Ipsum !")
                    )
                }
            }
        }

    suspend fun checkTokens(token: Token): Token? =
        when (tokenService.getTokenByAddress(token.tokenInfo.address)) {
            is Either.Left -> {
                tokenService.addToken(token.toSimpleToken())
                token
            }
            is Either.Right -> {
                null
            }
        }
}
