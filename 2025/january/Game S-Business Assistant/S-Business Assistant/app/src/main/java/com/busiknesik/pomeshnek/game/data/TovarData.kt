package com.busiknesik.pomeshnek.game.data

import kotlinx.serialization.Serializable

@Serializable
data class TovarData(
    var dName  : String,
    var date   : String,
    var summa  : Int,
    var count  : Int,
    var marge  : Int,
    var prible : Int,
)