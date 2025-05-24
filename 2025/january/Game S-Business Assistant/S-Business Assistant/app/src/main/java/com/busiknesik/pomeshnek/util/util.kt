package com.busiknesik.pomeshnek.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

fun log(message: String) {
    Log.i("businusese", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}