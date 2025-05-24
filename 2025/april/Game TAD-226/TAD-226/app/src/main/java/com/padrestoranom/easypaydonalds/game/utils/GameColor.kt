package com.padrestoranom.easypaydonalds.game.utils

import com.badlogic.gdx.graphics.Color

object GameColor {

    val background: Color = Color.valueOf("FFFFFF")

    val black_1B: Color = Color.valueOf("1B1C1F")
    val black_42: Color = Color.valueOf("424242")
    val blue_02 : Color = Color.valueOf("021D62")
    val red     : Color = Color.valueOf("FF3400")

    val black_1B_50 : Color = black_1B.cpy().apply { a = 0.5f }

}