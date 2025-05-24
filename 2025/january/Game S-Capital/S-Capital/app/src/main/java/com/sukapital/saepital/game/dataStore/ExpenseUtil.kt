package com.sukapital.saepital.game.dataStore

import com.sukapital.saepital.game.data.Expense
import com.sukapital.saepital.game.manager.GameDataStoreManager
import com.sukapital.saepital.util.log
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