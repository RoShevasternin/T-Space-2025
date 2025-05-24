package com.busiknesik.pomeshnek.game.dataStore

import com.busiknesik.pomeshnek.game.data.TovarData
import com.busiknesik.pomeshnek.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer

class DS_Tovar(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<TovarData>>(
    serializer   = ListSerializer(TovarData.serializer()),
    deserializer = ListSerializer(TovarData.serializer()),
) {

    override val dataStore = DataStoreManager.Tovar

    override val flow = MutableStateFlow(listOf<TovarData>())

    init { initialize() }

}