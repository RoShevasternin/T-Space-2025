//package com.cryonetpoint.ecsporush.game.dataStore
//
//import com.cryonetpoint.ecsporush.game.manager.DataStoreManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//
//class DS_Score(override val coroutine: CoroutineScope): DataStoreUtil<Int>(){
//
//    override val dataStore = DataStoreManager.Score
//
//    override val flow = MutableStateFlow(0)
//
//    init { initialize() }
//
//}