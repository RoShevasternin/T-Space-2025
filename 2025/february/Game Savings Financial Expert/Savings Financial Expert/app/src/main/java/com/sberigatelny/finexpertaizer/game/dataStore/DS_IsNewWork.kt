package com.sberigatelny.finexpertaizer.game.dataStore

import com.sberigatelny.finexpertaizer.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_IsNewWork(override val coroutine: CoroutineScope): DataStoreUtil<Boolean>() {

    override val dataStore = DataStoreManager.IsNewWork

    override val flow = MutableStateFlow(false)

    init { initialize() }

}
