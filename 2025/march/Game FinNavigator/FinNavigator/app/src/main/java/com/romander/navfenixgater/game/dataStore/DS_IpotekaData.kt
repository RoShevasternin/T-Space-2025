package com.romander.navfenixgater.game.dataStore

import com.romander.navfenixgater.game.manager.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

class DS_IpotekaData(override val coroutine: CoroutineScope): DataStoreJsonUtil<List<DataIpoteka>>(
    serializer   = ListSerializer(DataIpoteka.serializer()),
    deserializer = ListSerializer(DataIpoteka.serializer()),
) {

    override val dataStore = DataStoreManager.DataIpoteka

    override val flow = MutableStateFlow(listOf<DataIpoteka>())

    init { initialize() }

}

@Serializable
data class DataIpoteka(
    var nName           : String,
    val totalPayment   : Int,     // Сумма выплат
    val loanBody       : Int,         // Тело кредита
    val interestPaid   : Int,     // В том числе %
    val commissions    : Int       // Комисии
) : CalculationData