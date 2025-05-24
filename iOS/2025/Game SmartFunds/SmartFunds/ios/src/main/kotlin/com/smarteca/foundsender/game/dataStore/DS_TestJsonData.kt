//package com.smarteca.foundsender.game.dataStore
//
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.serialization.Serializable
//import kotlinx.serialization.builtins.ListSerializer
//
//class DS_TestJsonData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataItem>>(
//    serializer   = ListSerializer(DataItem.serializer()),
//    deserializer = ListSerializer(DataItem.serializer()),
//) {
//
//    //override val dataStore = DataStoreManager.ItemCount
//
//    override val flow = MutableStateFlow(listOf<DataItem>())
//
//    init { initialize() }
//
//}
//
//@Serializable
//data class DataItem(
//    var xp: Int = 0
//)
