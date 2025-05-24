package com.romander.navfenixgater.game.dataStore

import com.romander.navfenixgater.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_LizingData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataLizing>>(
    serializer   = ListSerializer(DataLizing.serializer()),
    deserializer = ListSerializer(DataLizing.serializer()),
) {

    override val dataStore = DataStoreManager.DataLizing

    override val flow = MutableStateFlow(listOf<DataLizing>())

    init { initialize() }

}

@Serializable
data class DataLizing(
    var nName           : String,
    var monthlyPayment  : Int,
    var objectCostFinal : Int,
    var totalPayments   : Int,
    var commission      : Int,
) : CalculationData

interface CalculationData
