package com.finansoviy.gurochek.game.dataStore

import com.finansoviy.gurochek.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class DS_Percent(override val coroutine: CoroutineScope): DataStoreJsonUtil<DataPercent>(
    serializer   = DataPercent.serializer(),
    deserializer = DataPercent.serializer(),
) {

    override val dataStore = DataStoreManager.DataPercent

    override val flow = MutableStateFlow(DataPercent())

    init { initialize() }

}

@Serializable
data class DataPercent(
    var quiz1: Int = 0,
    var quiz2: Int = 0,
    var quiz3: Int = 0,
    var quiz4: Int = 0,
    var quiz5: Int = 0,
)