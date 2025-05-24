package com.bagazkz.klarebadew.game.dataStore

import com.bagazkz.klarebadew.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gold(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.Gold

    override val flow = MutableStateFlow(0)

    init { initialize() }

}