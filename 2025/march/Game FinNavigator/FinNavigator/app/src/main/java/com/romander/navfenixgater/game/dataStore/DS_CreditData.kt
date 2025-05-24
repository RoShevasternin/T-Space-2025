package com.romander.navfenixgater.game.dataStore

import com.romander.navfenixgater.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_CreditData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataCredit>>(
    serializer   = ListSerializer(DataCredit.serializer()),
    deserializer = ListSerializer(DataCredit.serializer()),
) {

    override val dataStore = DataStoreManager.DataCredit

    override val flow = MutableStateFlow(listOf<DataCredit>())

    init { initialize() }

}

@Serializable
data class DataCredit(
    var nName           : String,
    val totalPayment    : Int,
    val creditBody      : Int,
    val interestAmount  : Int,
    val commission      : Int,
) : CalculationData