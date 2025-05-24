package com.borubashka.arsemajeg.game.data

import kotlinx.serialization.Serializable

@Serializable
data class DataLevel(
    var lvlNum: Int = 0,
    var stars : Int = -1,
    var isLock: Boolean = true,
)