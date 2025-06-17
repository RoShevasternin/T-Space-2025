package com.gazprombiznes.pygazprobiznes.game.dataStore

import com.gazprombiznes.pygazprobiznes.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Level(override val coroutine: CoroutineScope): DataStoreUtil<Int>(){

    override val dataStore = DataStoreManager.Level

    override val flow = MutableStateFlow(1)

    init { initialize() }

}