package com.borubashka.arsemajeg.game.dataStore

import com.borubashka.arsemajeg.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class DS_Score(override val coroutine: CoroutineScope): DataStoreUtil<Int>(){

    override val dataStore = DataStoreManager.Score

    override val flow = MutableStateFlow(0)

    init { initialize() }

}