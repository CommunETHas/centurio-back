package fr.hadaly.core

import fr.hadaly.core.model.SupportedChain
import fr.hadaly.nexusapi.model.Chain

fun Chain.toSupportedChain() = SupportedChain(this)
