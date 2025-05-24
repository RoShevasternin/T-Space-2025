package com.traoya.yatirimya.game.dataStore

import com.traoya.yatirimya.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gel(override val coroutine: CoroutineScope): DataStoreUtil<Int>(){

    override val dataStore = DataStoreManager.Gel

    override val flow = MutableStateFlow(0)

    init { initialize() }

}