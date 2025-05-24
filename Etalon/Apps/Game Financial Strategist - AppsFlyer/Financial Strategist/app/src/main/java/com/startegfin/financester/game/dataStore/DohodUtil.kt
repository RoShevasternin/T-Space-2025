package com.startegfin.financester.game.dataStore

import com.startegfin.financester.game.data.Dohod
import com.startegfin.financester.game.data.Roshod
import com.startegfin.financester.game.manager.GameDataStoreManager
import com.startegfin.financester.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DohodUtil(val coroutine: CoroutineScope) {

    var dohodListFlow = MutableStateFlow(listOf<Dohod>())
        private set

    init {
        coroutine.launch {
            val dohodListStr = GameDataStoreManager.DohodList.get()
            if (dohodListStr != null) dohodListFlow.value = Json.decodeFromString<List<Dohod>>(dohodListStr)

            log("Store Dohod:")
            dohodListFlow.value.onEachIndexed { index, dohod ->
                log("$index: $dohod")
            }
        }

    }

    fun update(block: (List<Dohod>) -> List<Dohod>) {
        coroutine.launch {
            dohodListFlow.value = block(dohodListFlow.value)

            log("Store Dohod update:")
            dohodListFlow.value.onEachIndexed { index, dohod ->
                log("$index: $dohod")
            }
            GameDataStoreManager.DohodList.update { Json.encodeToString<List<Dohod>>(dohodListFlow.value) }
        }
    }

}