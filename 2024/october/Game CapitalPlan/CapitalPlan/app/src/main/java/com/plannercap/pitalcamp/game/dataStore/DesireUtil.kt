package com.plannercap.pitalcamp.game.dataStore

import com.plannercap.pitalcamp.game.data.Desire
import com.plannercap.pitalcamp.game.manager.GameDataStoreManager
import com.plannercap.pitalcamp.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DesireUtil(val coroutine: CoroutineScope) {

    val desireListFlow = MutableStateFlow(mutableListOf<Desire>())

    init {
        coroutine.launch {
            val desireListStr = GameDataStoreManager.DesireList.get()
            if (desireListStr != null) desireListFlow.value = Json.decodeFromString<MutableList<Desire>>(desireListStr)

            log("Store Desire:")
            desireListFlow.value.onEachIndexed { index, desire ->
                log("$index: $desire")
            }
        }

    }

    fun update(block: (MutableList<Desire>) -> MutableList<Desire>) {
        coroutine.launch {
            desireListFlow.value = block(desireListFlow.value)

            log("Store Desire update:")
            desireListFlow.value.onEachIndexed { index, desire ->
                log("$index: $desire")
            }
            GameDataStoreManager.DesireList.update { Json.encodeToString<List<Desire>>(desireListFlow.value) }
        }
    }

}