package com.rayscaya.nasjajdenye.game.dataStore

import com.rayscaya.nasjajdenye.game.data.DataInput
import com.rayscaya.nasjajdenye.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class DS_DataInput(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataInput>>(
    serializer   = ListSerializer(DataInput.serializer()),
    deserializer = ListSerializer(DataInput.serializer()),
) {

    override val dataStore = DataStoreManager.DataInput

    override val flow = MutableStateFlow(listOf<DataInput>())

    init { initialize() }

}