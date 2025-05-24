package com.ogonechek.putinvestor.game.data

import kotlinx.serialization.Serializable

@Serializable
data class InvestData(
    var dName  : String,
    var period : Int,
    var percent: Int,
    var summa  : Int,
)