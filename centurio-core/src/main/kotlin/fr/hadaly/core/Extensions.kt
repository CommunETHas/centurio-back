package fr.hadaly.core

import fr.hadaly.core.model.Chain
import fr.hadaly.core.model.SupportedChain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

fun Chain.toSupportedChain() = SupportedChain(this)

suspend fun <A, B>List<A>.parmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async(Dispatchers.Default) { f(it) } }.awaitAll()
}
