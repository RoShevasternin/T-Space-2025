package com.liberator.wisoliter.game.dataStore

import com.liberator.wisoliter.game.manager.DataStoreManager
import com.liberator.wisoliter.game.utils.COUNTRY_COUNT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class DS_CountryUron(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<Int>>(
    serializer   = ListSerializer(Int.serializer()),
    deserializer = ListSerializer(Int.serializer()),
) {

    override val dataStore = DataStoreManager.CountryUron

    override val flow = MutableStateFlow(List(COUNTRY_COUNT) { 0 })

    init { initialize() }

}