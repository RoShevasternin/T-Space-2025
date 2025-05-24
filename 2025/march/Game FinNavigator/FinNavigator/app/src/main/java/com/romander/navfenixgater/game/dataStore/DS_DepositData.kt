package com.romander.navfenixgater.game.dataStore

import com.romander.navfenixgater.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_DepositData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataDeposit>>(
    serializer   = ListSerializer(DataDeposit.serializer()),
    deserializer = ListSerializer(DataDeposit.serializer()),
) {

    override val dataStore = DataStoreManager.DataDeposit

    override val flow = MutableStateFlow(listOf<DataDeposit>())

    init { initialize() }

}

@Serializable
data class DataDeposit(
    var nName           : String,
    val totalBody    : Int,          // Тіло вкладу (внесено коштів)
    val totalInterest: Int       // Нараховані проценти
) : CalculationData