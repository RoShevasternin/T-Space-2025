package com.rugosbon.pybonesrus.game.data

import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
    var isDohod      : Boolean = false,
    var summa        : Int = 0,
    var categoryIndex: Int = -1,
    var coment       : String = "",
    var date         : String = "",
)