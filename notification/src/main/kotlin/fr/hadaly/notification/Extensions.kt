import fr.hadaly.core.model.Recommandation
import fr.hadaly.core.model.Recommandations

fun Recommandations.toEmailText(address: String): String =
    """
        Hello !
        Following your frequency preferences, we scanned your wallet $address and
        have the following recommandations for you :
        
        ${this.recommandations.joinToString("\n") { it.toTextList() }}
        
        For any questions, remarks contact us at : team@centurio.app
    """.trimIndent()


fun Recommandation.toTextList() =
    """
    - ${this.cover.name}
""".trimIndent()
