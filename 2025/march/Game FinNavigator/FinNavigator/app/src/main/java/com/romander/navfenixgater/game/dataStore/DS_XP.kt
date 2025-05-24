package com.romander.navfenixgater.game.dataStore

import com.romander.navfenixgater.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_XP(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {

    override val dataStore = DataStoreManager.XP

    override val flow = MutableStateFlow(0)

    init { initialize() }

}