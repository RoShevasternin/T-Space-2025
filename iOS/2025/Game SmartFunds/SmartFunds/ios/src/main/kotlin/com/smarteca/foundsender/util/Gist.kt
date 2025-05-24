package com.smarteca.foundsender.util

import com.smarteca.foundsender.game.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.robovm.apple.foundation.*
import kotlin.coroutines.resume

object Gist {
    private const val URL_STRING = "https://gist.githubusercontent.com/ValenDula/65647033a7e09bb6b4052742c721c9ee/raw/com.smarteca.foundsender"

    suspend fun getDataJson(): DataJSON? = withContext(Dispatchers.IO) {
        try {
            val url = NSURL(URL_STRING)
            val request = NSURLRequest(url)
            val session = NSURLSession.getSharedSession()

            suspendCancellableCoroutine { continuation ->
                val task = session.newDataTask(request) { data, _, error ->
                    if (error != null) {
                        log("Gist = HTTP Error: ${error.localizedDescription}")
                        continuation.resume(null)
                        return@newDataTask
                    }

                    if (data != null) {
                        val responseString = NSString(data, NSStringEncoding.UTF8)
                        val json = JSONObject(responseString.toString())
                        log(json.toString())

                        continuation.resume(
                            DataJSON(
                                dom = json.optString("dom", ""),
                                dev = json.optString("dev", ""),
                                app = json.optString("app", ""),
                                isGame = json.optString("isGame", ""),
                            )
                        )
                    } else {
                        continuation.resume(null)
                    }
                }

                // Запускаємо запит
                task.resume()

                // Скасовуємо запит, якщо корутина скасовується
                continuation.invokeOnCancellation { task.cancel() }
            }
        } catch (e: Exception) {
            log("Gist = Exception: ${e.message}")
            null
        }
    }

    data class DataJSON(
        val dom: String,
        val dev: String,
        val app: String,
        val isGame: String,
    )

}

