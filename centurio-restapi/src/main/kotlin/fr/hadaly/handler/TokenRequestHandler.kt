package fr.hadaly.handler

import arrow.core.Either
import arrow.core.getOrElse
import fr.hadaly.core.model.SimpleToken
import fr.hadaly.core.service.CoverRepository
import fr.hadaly.core.service.TokenRepository
import fr.hadaly.engine.toSimpleToken
import fr.hadaly.ethplorer.EthplorerService
import fr.hadaly.model.TokenRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class TokenRequestHandler(
    val tokenRepository: TokenRepository,
    val coverRepository: CoverRepository,
    val ethplorerService: EthplorerService
) {

    private val logger = LoggerFactory.getLogger(TokenRequestHandler::class.java)

    suspend fun handle(requests: List<TokenRequest>) {
        requests.forEach { handleRequest(it) }
    }

    private suspend fun handleRequest(request: TokenRequest) = withContext(Dispatchers.IO) {
        when(val tokenResult = handleKnownToken(request.address)) {
            is Either.Left -> {
                handleUnknownToken(request)
            }
            is Either.Right -> {
                associateCoverToToken(tokenResult.value, request)
            }
        }
    }

    private suspend fun associateCoverToToken(
        token: SimpleToken,
        tokenRequest: TokenRequest
    ) {
        val covers = tokenRequest.covers.map {
            coverRepository.getCoverByAddress(it).getOrElse { null }
        }
        val updatedToken = token.copy(recommendedCovers = covers.filterNotNull())
        tokenRepository.updateToken(updatedToken)
    }

    private suspend fun handleKnownToken(address: String): Either<Throwable, SimpleToken> =
        tokenRepository.getTokenByAddress(address)

    private suspend fun handleUnknownToken(request: TokenRequest) {
        logger.info("Fetching unknown token with address : ${request.address}")
        when(val tokenInfo = ethplorerService.getTokenInfo(request.address)) {
            is Either.Left -> {
                logger.error(tokenInfo.value.message)
            }
            is Either.Right -> {
                val token = tokenInfo.value.toSimpleToken()
                tokenRepository.addToken(token)
                associateCoverToToken(token, request)
            }
        }
    }
}
