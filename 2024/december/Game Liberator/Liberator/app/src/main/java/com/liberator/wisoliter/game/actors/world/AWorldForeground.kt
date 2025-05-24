package com.liberator.wisoliter.game.actors.world

import com.liberator.wisoliter.game.utils.COUNTRY_COUNT
import com.liberator.wisoliter.game.utils.SizeScaler
import com.liberator.wisoliter.game.utils.WORLD_WIDTH
import com.liberator.wisoliter.game.utils.actor.PosSize
import com.liberator.wisoliter.game.utils.actor.setBoundsScaled
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.game.utils.parsePolygonFromJson
import com.liberator.wisoliter.util.log

class AWorldForeground(override val screen: AdvancedScreen) : AdvancedGroup() {

    override val sizeScaler = SizeScaler(SizeScaler.Axis.X, WORLD_WIDTH)

    private val listCountryXP = listOf(
        32, 100, 50, 74, 15, 45, 17, 25, 5, 10,
        12, 9, 1, 333, 2, 8, 7, 11, 4, 3,
        16, 2, 9, 90, 6, 1, 60, 8, 1, 77
    ).map { it * 1000 }

    val jsonPath  = "poligons.json"
    val lisPolygon = List(COUNTRY_COUNT) { parsePolygonFromJson(jsonPath, it.inc().toString()) }

    private val listCountry = List(COUNTRY_COUNT) {
        ACountry(
            screen,
            gdxGame.assetsAll.listCountry[it],
            lisPolygon[it],

            xp   = listCountryXP[it],
            uron = if (it.inc() == 14) listCountryXP[it] else gdxGame.ds_CountryUron.flow.value[it],
            it,
        )
    }

    // Field
    private val listPosSize = listOf(
        PosSize(0f, 2990f, 1696f, 852f),
        PosSize(1329f, 2503f, 2740f, 1670f),
        PosSize(3624f, 2985f, 1336f, 1127f),
        PosSize(1454f, 1687f, 1900f, 1087f),
        PosSize(2651f, 937f, 940f, 866f),
        PosSize(2888f, 762f, 1235f, 886f),
        PosSize(2983f, 177f, 576f, 985f),
        PosSize(5302f, 2826f, 707f, 1054f),
        PosSize(4893f, 2668f, 348f, 379f),
        PosSize(4912f, 2319f, 555f, 450f),

        PosSize(5343f, 2293f, 701f, 604f),
        PosSize(5581f, 2434f, 481f, 532f),
        PosSize(5842f, 2721f, 266f, 136f),
        PosSize(5823f, 2536f, 521f, 214f),
        PosSize(5953f, 2150f, 745f, 363f),
        PosSize(5205f, 1506f, 1609f, 746f),
        PosSize(4652f, 1625f, 998f, 724f),
        PosSize(5394f, 1156f, 1127f, 656f),
        PosSize(5549f, 784f, 1452f, 656f),
        PosSize(6261f, 1790f, 797f, 446f),

        PosSize(6532f, 2466f, 1200f, 414f),
        PosSize(6509f, 2043f, 1001f, 535f),
        PosSize(7322f, 1295f, 2340f, 980f),
        PosSize(7220f, 1932f, 1937f, 999f),
        PosSize(7742f, 2523f, 974f, 303f),
        PosSize(8327f, 1717f, 323f, 334f),
        PosSize(8889f, 1652f, 669f, 1054f),
        PosSize(9653f, 1013f, 1214f, 860f),
        PosSize(10009f, 24f, 639f, 614f),
        PosSize(8704f, 410f, 1282f, 904f),
    )

    var blockSelectCountry: (ACountry) -> Unit = {}
    var blockZahvachenoCountry = {}

    override fun addActorsOnGroup() {
        addListCountry()
    }

    // Actors -----------------------------------------------------------------------------------

    private fun addListCountry() {
        listCountry.onEachIndexed { index, country ->
            addActor(country)
            country.setBoundsScaled(
                sizeScaler,
                listPosSize[index].x,
                listPosSize[index].y,
                listPosSize[index].w,
                listPosSize[index].h
            )

            country.blockSelect = {
                log("select country: ${index.inc()}")
                blockSelectCountry(it)
            }
            country.blockZahvacheno = { blockZahvachenoCountry() }

        }
    }

    // Logic ------------------------------------------------------------------------------

    fun tutorial() {
        listCountry[12].tutorial()
    }

}