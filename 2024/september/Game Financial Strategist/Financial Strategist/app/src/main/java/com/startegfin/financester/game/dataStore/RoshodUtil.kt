package com.startegfin.financester.game.dataStore

import com.startegfin.financester.game.data.Roshod
import com.startegfin.financester.game.manager.GameDataStoreManager
import com.startegfin.financester.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoshodUtil(val coroutine: CoroutineScope) {

    var roshodListFlow = MutableStateFlow(listOf<Roshod>())
        private set

    init {
        coroutine.launch {
            val roshpdListStr = GameDataStoreManager.RoshodList.get()
            if (roshpdListStr != null) roshodListFlow.value = Json.decodeFromString<List<Roshod>>(roshpdListStr)

            log("Store Roshod:")
            roshodListFlow.value.onEachIndexed { index, roshod ->
                log("$index: $roshod")
            }
        }

    }

    fun update(block: (List<Roshod>) -> List<Roshod>) {
        coroutine.launch {
            roshodListFlow.value = block(roshodListFlow.value)

            log("Store Roshod update:")
            roshodListFlow.value.onEachIndexed { index, roshod ->
                log("$index: $roshod")
            }
            GameDataStoreManager.RoshodList.update { Json.encodeToString<List<Roshod>>(roshodListFlow.value) }
        }
    }

}