package com.sburbanaizer.verginiafalesska.game.dataStore

import com.sburbanaizer.verginiafalesska.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class DS_Level(override val coroutine: CoroutineScope): DataStoreJsonUtil<DataLevel>(
    serializer   = DataLevel.serializer(),
    deserializer = DataLevel.serializer(),
) {

    override val dataStore = DataStoreManager.Level

    override val flow = MutableStateFlow(DataLevel(1, 1))

    init { initialize() }

}

@Serializable
data class DataLevel(var lvlDobicha: Int, var lvlProdaja: Int) {
    override fun equals(other: Any?): Boolean {
        return false
    }
}