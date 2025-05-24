package com.vectorvesta.bronfinteh.game.data

import com.badlogic.gdx.Input

data class DataItem(
    var resultText: String,
    val title: String,
    val inputType: Input.OnscreenKeyboardType,
    val blockFormatText: (String) -> String,
)
