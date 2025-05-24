//package com.liberator.wisoliter.game.dataStore
//
//import com.liberator.wisoliter.game.manager.DataStoreManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//
//class DS_XP(override val coroutine: CoroutineScope): DataStoreUtil<Int>() {
//
//    override val dataStore = DataStoreManager.XP
//
//    override val flow = MutableStateFlow(50)
//
//    init { initialize() }
//
//}