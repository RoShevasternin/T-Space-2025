package com.funkun.kylkan.game.dataStore

import com.funkun.kylkan.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_TripData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<TripData>>(
    serializer   = ListSerializer(TripData.serializer()),
    deserializer = ListSerializer(TripData.serializer()),
) {

    override val dataStore = DataStoreManager.TripData

    override val flow = MutableStateFlow(listOf<TripData>())

    init { initialize() }

}

@Serializable
data class TripData(
    val nameTrip : String,
    val dateStart: String,
    val dateEnd  : String,
    val summa    : Int,
    val listTrat : MutableList<Trata> = mutableListOf<Trata>()
)

@Serializable
data class Trata(
    val name : String,
    val summa: Int,
)