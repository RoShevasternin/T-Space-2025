package com.sberigatelny.finexpertaizer.game.dataStore

import com.sberigatelny.finexpertaizer.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_IsPereezd(override val coroutine: CoroutineScope): DataStoreUtil<Boolean>() {

    override val dataStore = DataStoreManager.IsPereezd

    override val flow = MutableStateFlow(false)

    init { initialize() }

}
