package com.ogonechek.putinvestor.game.dataStore

import com.ogonechek.putinvestor.game.data.InvestData
import com.ogonechek.putinvestor.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer

class DS_Invest(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<InvestData>>(
    serializer   = ListSerializer(InvestData.serializer()),
    deserializer = ListSerializer(InvestData.serializer()),
) {

    override val dataStore = DataStoreManager.Invest

    override val flow = MutableStateFlow(listOf<InvestData>())

    init { initialize() }

}