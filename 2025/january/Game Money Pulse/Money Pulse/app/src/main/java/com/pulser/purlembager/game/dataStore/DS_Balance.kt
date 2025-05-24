//package com.pulser.purlembager.game.dataStore
//
//import com.pulser.purlembager.game.manager.DataStoreManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//
//class DS_Balance(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {
//
//    override val dataStore = DataStoreManager.Balance
//
//    override val flow = MutableStateFlow(0)
//
//    init { initialize() }
//
//}