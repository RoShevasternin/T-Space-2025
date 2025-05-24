package com.sburbanaizer.verginiafalesska.game.dataStore

import com.sburbanaizer.verginiafalesska.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Maslo(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Maslo

    override val flow = MutableStateFlow(0)

    init { initialize() }

}