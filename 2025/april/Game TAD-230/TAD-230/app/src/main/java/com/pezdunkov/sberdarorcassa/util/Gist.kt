package com.pezdunkov.sberdarorcassa.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

object Gist {

    private const val URL_STRING = "https://gist.githubusercontent.com/ValenDula/0260ee6031c12d5d453d792840e750f9/raw/com.pezdunkov.sberdarorcassa"

    suspend fun getDataJson(): DataJSON? = withContext(Dispatchers.IO) {
        try {
            val url = URL(URL_STRING)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000  // 5 секунд таймаут на з'єднання
            connection.readTimeout = 5000  // 5 секунд таймаут на читання
            connection.doInput = true

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = connection.inputStream
                val response = inputStream.readBytes().toString(Charsets.UTF_8) // Читаємо відповідь у String
                inputStream.close()

                val json = JSONObject(response)
                log(json.toString())

                DataJSON(
                    lor  = json.optString("lor", ""),
                    koba = json.optString("koba", "")
                )
            } else {
                log("Gist = HTTP Error: ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            log("Gist = Exception: ${e.message}")
            null
        }
    }

    data class DataJSON(val lor: String, val koba: String)

}