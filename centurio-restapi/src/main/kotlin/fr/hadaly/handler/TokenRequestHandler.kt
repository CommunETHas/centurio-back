package fr.hadaly.handler

import arrow.core.Either
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.parmap
import fr.hadaly.core.repository.TokenRepository
import fr.hadaly.core.service.CoverRepository
import fr.hadaly.model.TokenRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class TokenRequestHandler(
    private val tokenRepository: TokenRepository,
    private val coverRepository: CoverRepository,
) {

    private val logger = LoggerFactory.getLogger(TokenRequestHandler::class.java)

    suspend fun handle(requests: List<TokenRequest>) {
        val tokens = requests.parmap { handleRequest(it) }.mapNotNull { it.orNull() }
        val updatedTokens = tokens.map { associateCoverToToken(it.first, it.second) }
        tokenRepository.updateTokens(updatedTokens)
    }

    private suspend fun handleRequest(request: TokenRequest): Either<Throwable, Pair<SimpleToken, List<String>>> =
        withContext(Dispatchers.IO) {
            when (val tokenResult = tokenRepository.getTokenByAddress(request.address)) {
                is Either.Left -> {
                    logger.error("Failed handling request : ${tokenResult.value.message}")
                    tokenResult
                }
                is Either.Right -> {
                    tokenResult.map { it to request.covers }
                }
            }
        }

    private suspend fun associateCoverToToken(
        token: SimpleToken,
        coverAddresses: List<String>
    ): SimpleToken {
        val covers = coverAddresses.mapNotNull {
            coverRepository.getCoverByAddress(it).orNull()
        }
        return token.copy(recommendedCovers = covers)
    }

    suspend fun getToken(address: String) = tokenRepository.getTokenByAddress(address)

    suspend fun getTokens() = tokenRepository.getTokens()
}
