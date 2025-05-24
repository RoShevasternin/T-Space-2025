package com.smarteca.foundsender.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.foundation.NSProcessInfo
import kotlin.coroutines.CoroutineContext

// IOSDispatcher - запускає корутину в UI-потоці iOS!
object IOSDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        DispatchQueue.getMainQueue().async { block.run() }
    }
}

fun cancelCoroutineAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}
