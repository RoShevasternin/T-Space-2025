package com.startegfin.financester.game.utils

import com.badlogic.gdx.graphics.Color

object GColor {

    val background = Color.valueOf("F3F6FB")
    val text       = Color.valueOf("2C3137")
    val red        = Color.valueOf("CD0000")
    val green      = Color.valueOf("2DC553")
    val foreground = Color.valueOf("2C3137").apply { a = 0.70f }

    val statisticRoshodColors = listOf(
        Color.valueOf("55CD73"),
        Color.valueOf("FFD963"),
        Color.valueOf("FD5A3E"),
        Color.valueOf("77B6E7"),
        Color.valueOf("A955B8"),
        Color.valueOf("97CC64"),
    )

    val statisticDohodColors = listOf(
        Color.valueOf("55CD73"),
        Color.valueOf("77B6E7"),
        Color.valueOf("FFD963"),
    )

}