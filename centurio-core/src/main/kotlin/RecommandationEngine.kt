import fr.hadaly.EthplorerServiceImpl
import org.slf4j.LoggerFactory

class RecommandationEngine {
    private val logger = LoggerFactory.getLogger("RecommandationEngine")
    private val ethplorerService = EthplorerServiceImpl()

    suspend fun recommendFor(address: String) {
        val walletInfo = ethplorerService.getWalletInfo(address)
        logger.info("Checking recommandation for $address with ${walletInfo.transactionCount} transactions.")
    }

}
