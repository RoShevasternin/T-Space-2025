package com.rayscaya.nasjajdenye.game.data

import kotlinx.serialization.Serializable

@Serializable
data class DataInput(
    var title   : String,
    var period  : Int,
    var percent : Int,
    var summa   : Int,
)
