package com.smarteca.foundsender.game.dataStore

import com.smarteca.foundsender.game.manager.JsonStorageManager
import com.smarteca.foundsender.game.utils.log
import com.smarteca.foundsender.game.utils.runGDX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class DataStoreJsonUtil<T>(
    private val serializer  : KSerializer<T>,
    private val deserializer: DeserializationStrategy<T>
) {
    val simpleName: String get() = this::class.java.simpleName

    abstract val coroutine: CoroutineScope
    abstract val flow     : MutableStateFlow<T>

    open fun initialize() {
        JsonStorageManager.loadStringFromFile(simpleName)?.let { loadValue -> flow.value = Json.decodeFromString(deserializer, loadValue) }
        log("Store $simpleName = ${flow.value}")
    }

    open fun update(block: (T) -> T) {
        coroutine.launch(Dispatchers.IO) {
            flow.value = block(flow.value)

            log("Store $simpleName update = ${flow.value}")
            val saveJson = Json.encodeToString(serializer, flow.value)
            JsonStorageManager.saveStringToFile(simpleName, saveJson)
        }
    }
}
