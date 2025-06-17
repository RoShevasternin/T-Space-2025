package com.gazprombiznes.pygazprobiznes.game.dataStore

import com.gazprombiznes.pygazprobiznes.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Gaz(override val coroutine: CoroutineScope): DataStoreUtil<Int>(){

    override val dataStore = DataStoreManager.Gaz

    override val flow = MutableStateFlow(0)

    init { initialize() }

}