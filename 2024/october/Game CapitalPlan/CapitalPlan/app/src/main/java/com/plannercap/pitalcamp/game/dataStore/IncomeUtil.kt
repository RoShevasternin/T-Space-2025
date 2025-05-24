package com.plannercap.pitalcamp.game.dataStore

import com.plannercap.pitalcamp.game.data.Income
import com.plannercap.pitalcamp.game.manager.GameDataStoreManager
import com.plannercap.pitalcamp.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IncomeUtil(val coroutine: CoroutineScope) {

    val incomeListFlow = MutableStateFlow(mutableListOf<Income>())

    init {
        coroutine.launch {
            val incomeListStr = GameDataStoreManager.IncomeList.get()
            if (incomeListStr != null) incomeListFlow.value = Json.decodeFromString<MutableList<Income>>(incomeListStr)

            log("Store Desire:")
            incomeListFlow.value.onEachIndexed { index, income ->
                log("$index: $income")
            }
        }

    }

    fun update(block: (MutableList<Income>) -> MutableList<Income>) {
        coroutine.launch {
            incomeListFlow.value = block(incomeListFlow.value)

            log("Store Desire update:")
            incomeListFlow.value.onEachIndexed { index, income ->
                log("$index: $income")
            }
            GameDataStoreManager.IncomeList.update { Json.encodeToString<List<Income>>(incomeListFlow.value) }
        }
    }

}