package fr.hadaly.engine

import fr.hadaly.core.model.Reasoning
import fr.hadaly.core.model.Recommandation
import fr.hadaly.core.model.Recommandations
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.ethplorer.EthplorerService
import fr.hadaly.ethplorer.model.Token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.slf4j.LoggerFactory
import java.util.NoSuchElementException

class RecommandationEngine(
    private val ethplorerService: EthplorerService,
    private val tokenService: TokenRepository
) {
    private val logger = LoggerFactory.getLogger("fr.hadaly.core.RecommandationEngine")

    suspend fun recommendFor(address: String): Recommandations {
        val walletInfo = ethplorerService.getWalletInfo(address)
        logger.info("Checking recommandation for $address with ${walletInfo.transactionCount} transactions.")
        val unsupportedTokens = walletInfo.tokens.mapNotNull { checkTokens(it) }
        val recommandations =
            (walletInfo.tokens - unsupportedTokens).map { tokenService.getTokenByAddress(it.tokenInfo.address) }
                .flatMap {
                    it.recommendedCovers.map { cover ->
                        Recommandation(
                            cover,
                            Reasoning(it.symbol, "Lorem ipsum my friend, Lorem Ipsum !")
                        )
                    }
                }.toSet().toList()
        return Recommandations(
            recommandations.size,
            recommandations = recommandations,
            unsuportedTokens = unsupportedTokens.map { it.toSimpleToken() }
        )
    }

    suspend fun checkTokens(token: Token): Token? {
        val job = CoroutineScope(Dispatchers.Default).async {
            tokenService.getTokenByAddress(token.tokenInfo.address)
        }
        return try {
            val result = job.await()
            return if (result.recommendedCovers.isEmpty()) {
                token
            } else {
                null
            }
        } catch (error: NoSuchElementException) {
            tokenService.addToken(token.toSimpleToken())
            token
        }
    }
}
