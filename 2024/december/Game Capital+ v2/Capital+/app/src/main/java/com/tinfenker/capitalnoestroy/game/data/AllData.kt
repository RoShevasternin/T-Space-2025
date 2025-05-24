package com.tinfenker.capitalnoestroy.game.data

import kotlinx.serialization.Serializable

@Serializable
data class Contribution(
    val uuid   : Long,
    val title  : String,
    val period : Int,
    val percent: Int,
    val summa  : Int,
)
