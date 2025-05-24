package com.proccaptald.proffesionalestic.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

fun log(message: String) {
    Log.i("Burbek", message)
}

fun cancelCoroutinesAll(vararg coroutine: CoroutineScope?) {
    coroutine.forEach { it?.cancel() }
}

fun String?.isNullOrText(text: String): Boolean = (this == null || this == text)

fun String?.isNotNullAndEmptyAndText(text: String): Boolean = (this != null && this != "" && this != text)
