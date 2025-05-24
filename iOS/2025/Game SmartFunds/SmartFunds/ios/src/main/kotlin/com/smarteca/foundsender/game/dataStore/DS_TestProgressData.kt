package com.smarteca.foundsender.game.dataStore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class DS_TestProgressData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<Int>>(
    serializer   = ListSerializer(Int.serializer()),
    deserializer = ListSerializer(Int.serializer()),
) {

    override val flow = MutableStateFlow(List(4) { -1 })

}
