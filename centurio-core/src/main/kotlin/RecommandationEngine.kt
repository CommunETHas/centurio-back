import fr.hadaly.EthplorerServiceImpl

class RecommandationEngine {
    private val ethplorerService = EthplorerServiceImpl()

    suspend fun recommendFor(address: String) {
        val walletInfo = ethplorerService.getWalletInfo(address)
        println(walletInfo)
    }

}
