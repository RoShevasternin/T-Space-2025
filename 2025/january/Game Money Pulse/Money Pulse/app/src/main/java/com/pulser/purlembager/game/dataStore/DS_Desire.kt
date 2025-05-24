package com.pulser.purlembager.game.dataStore

import com.pulser.purlembager.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_Desire(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataDesire>>(
    serializer   = ListSerializer(DataDesire.serializer()),
    deserializer = ListSerializer(DataDesire.serializer()),
) {

    override val dataStore = DataStoreManager.Desire

    override val flow = MutableStateFlow(listOf<DataDesire>())

    init { initialize() }

}

@Serializable
data class DataDesire(
    var tName : String,
    var tSumma: Int,
    var tDate : String,
    var tDay  : String,
)