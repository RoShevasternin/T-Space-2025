package com.goskapmepdup.dalaylamek

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import kotlin.collections.toString
import kotlin.io.readBytes

object Gist {

    private const val URL_STRING = "https://gist.githubusercontent.com/ValenDula/05d0db3221cc28c1d7fc7b08c3a4a61a/raw/com.goskapmepdup.dalaylamek"

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
                Log.e("MY_TAG", json.toString())

                DataJSON(
                    aram   = json.optString("aram", ""),
                    zamaev = json.optString("zamaev", "")
                )
            } else {
                Log.e("MY_TAG", "Gist = HTTP Error: ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            Log.e("MY_TAG", "Gist = Exception: ${e.message}")
            null
        }
    }

    data class DataJSON(val aram: String, val zamaev: String)

}