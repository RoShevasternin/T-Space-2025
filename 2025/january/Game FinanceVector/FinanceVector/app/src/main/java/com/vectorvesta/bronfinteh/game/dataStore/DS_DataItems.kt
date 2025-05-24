package com.vectorvesta.bronfinteh.game.dataStore

import com.vectorvesta.bronfinteh.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_DataItems(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataItems>>(
    serializer   = ListSerializer(DataItems.serializer()),
    deserializer = ListSerializer(DataItems.serializer()),
) {

    override val dataStore = DataStoreManager.DataItems

    override val flow = MutableStateFlow(listOf<DataItems>())

    init { initialize() }

}

@Serializable
data class DataItems(
    val type: DataItemType,
    val listValues: List<String>,
)

@Serializable
enum class DataItemType {
    Leasing, Loan, Deposit, Investments, Mortgage
}