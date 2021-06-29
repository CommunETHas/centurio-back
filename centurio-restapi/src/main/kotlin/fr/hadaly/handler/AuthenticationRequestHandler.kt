package fr.hadaly.handler

import arrow.core.Either
import fr.hadaly.core.model.User
import org.web3j.crypto.ECDSASignature
import org.web3j.crypto.Keys
import org.web3j.crypto.Sign
import org.web3j.crypto.Sign.recoverFromSignature
import org.web3j.utils.Numeric
import java.math.BigInteger

/**
 * Implementation based on :
 * [ECRecoverTest](github.com/PlatONnetwork/client-sdk-java/crypto/src/test/java/com/platon/crypto/ECRecoverTest.java)
 */
class AuthenticationRequestHandler {
    private val prefixMessage = "Welcome to Centurio, sign this message to authenticate !"

    fun verify(user: User, signature: String): Either<Throwable, Boolean> = Either.catch {
        val messageHash = org.web3j.crypto.Hash.sha3("$prefixMessage ${user.nonce}").toByteArray()
        val signatureData = getSignatureData(signature)
        var matchFound = false
        for (recId in 0 until 4) {
            val publicKey = recoverFromSignature(
                recId,
                ECDSASignature(BigInteger(1, signatureData.r), BigInteger(1, signatureData.s)),
                messageHash
            )

            if (publicKey != null) {
                val recoveredAddress = "0x${Keys.getAddress(publicKey)}"
                if (recoveredAddress == user.address) {
                    matchFound = true
                    break
                }
            }
        }
        matchFound
    }

    private fun getSignatureData(signature: String): Sign.SignatureData {
        val nonceBytes = Numeric.hexStringToByteArray(signature)
        var v = nonceBytes[64]
        if (v < 27) {
            v = (v + 27).toByte()
        }
        return Sign.SignatureData(
            v,
            nonceBytes.sliceArray(0..32),
            nonceBytes.sliceArray(32..64)
        )
    }
}
