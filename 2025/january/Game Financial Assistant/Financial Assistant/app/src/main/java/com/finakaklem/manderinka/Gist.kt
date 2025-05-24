package com.finakaklem.manderinka

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

    private const val URL_STRING = "https://gist.githubusercontent.com/ValenDula/19faea6ca9423c0bedc72bf0ef0c6342/raw/com.finakaklem.manderinka"

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
                Log.d("MY_TAG", json.toString())

                DataJSON(
                    apiUrl   = json.optString("apiUrl", ""),
                    devKey = json.optString("devKey", "")
                )
            } else {
                Log.d("MY_TAG", "Gist = HTTP Error: ${connection.responseCode}")
                null
            }
        } catch (e: Exception) {
            Log.d("MY_TAG","Gist = Exception: ${e.message}")
            null
        }
    }

    data class DataJSON(val apiUrl: String, val devKey: String)

}