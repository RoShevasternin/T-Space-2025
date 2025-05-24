package com.plannercap.pitalcamp.game.dataStore

import com.plannercap.pitalcamp.game.data.Expense
import com.plannercap.pitalcamp.game.data.Income
import com.plannercap.pitalcamp.game.manager.GameDataStoreManager
import com.plannercap.pitalcamp.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ExpenseUtil(val coroutine: CoroutineScope) {

    val expenseListFlow = MutableStateFlow(mutableListOf<Expense>())

    init {
        coroutine.launch {
            val expenseListStr = GameDataStoreManager.ExpenseList.get()
            if (expenseListStr != null) expenseListFlow.value = Json.decodeFromString<MutableList<Expense>>(expenseListStr)

            log("Store Desire:")
            expenseListFlow.value.onEachIndexed { index, expense ->
                log("$index: $expense")
            }
        }

    }

    fun update(block: (MutableList<Expense>) -> MutableList<Expense>) {
        coroutine.launch {
            expenseListFlow.value = block(expenseListFlow.value)

            log("Store Desire update:")
            expenseListFlow.value.onEachIndexed { index, expense ->
                log("$index: $expense")
            }
            GameDataStoreManager.ExpenseList.update { Json.encodeToString<List<Expense>>(expenseListFlow.value) }
        }
    }

}