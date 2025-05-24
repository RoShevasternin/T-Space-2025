package com.traoya.yatirimya.game.dataStore

import com.traoya.yatirimya.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_IsTutorial(override val coroutine: CoroutineScope): DataStoreUtil<Boolean>(){

    override val dataStore = DataStoreManager.IsTutorial

    override val flow = MutableStateFlow(true)

    init { initialize() }

}