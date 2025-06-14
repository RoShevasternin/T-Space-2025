package com.rugosbon.pybonesrus.game.dataStore

import com.rugosbon.pybonesrus.game.data.DataItem
import com.rugosbon.pybonesrus.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer

class DS_ItemData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataItem>>(
    serializer   = ListSerializer(DataItem.serializer()),
    deserializer = ListSerializer(DataItem.serializer()),
) {

    override val dataStore = DataStoreManager.DataIncome

    override val flow = MutableStateFlow(listOf<DataItem>())

    init { initialize() }

}