package com.borocbernay.kasshsemir.game.data

import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
    var isProdano     : Boolean = false,
    var nName         : String = "",
    var date          : String = "",
    var kilkist       : Int = 0,
    var priceZakupu1  : Int = 0,
    var priceProdaja1 : Int = 0,
    var nalog         : Int = 0,
    var marja         : Int = 0,

    var kilkistProdano: Int = 0,
)