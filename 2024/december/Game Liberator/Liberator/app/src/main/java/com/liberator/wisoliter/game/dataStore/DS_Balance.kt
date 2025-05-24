package com.liberator.wisoliter.game.dataStore

import com.liberator.wisoliter.game.manager.DataStoreManager
import com.liberator.wisoliter.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DS_Balance(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Balance

    override val flow = MutableStateFlow(100)

    init { initialize() }

}