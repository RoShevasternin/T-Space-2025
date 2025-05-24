package com.pulser.purlembager.game.dataStore

import com.pulser.purlembager.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_Transactions(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataTransaction>>(
    serializer   = ListSerializer(DataTransaction.serializer()),
    deserializer = ListSerializer(DataTransaction.serializer()),
) {

    override val dataStore = DataStoreManager.Transactions

    override val flow = MutableStateFlow(listOf<DataTransaction>())

    init { initialize() }

}

@Serializable
data class DataTransaction(
    val type  : DataTransactionType,
    var tName : String,
    var tSumma: Int,
)

@Serializable
enum class DataTransactionType {
    Income, Expense
}