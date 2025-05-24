package com.padrestoranom.easypaydonalds.game.data

import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
    var isIncome     : Boolean = false,
    var summa        : Int = 0,
    var categoryIndex: Int = -1,
    var date         : String = "",
)