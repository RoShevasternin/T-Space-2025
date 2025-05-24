package com.sberigatelny.finexpertaizer.game.dataStore

import com.sberigatelny.finexpertaizer.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_IsTutorial(override val coroutine: CoroutineScope): DataStoreUtil<Boolean>() {

    override val dataStore = DataStoreManager.IsTutorial

    override val flow = MutableStateFlow(true)

    init { initialize() }

}
