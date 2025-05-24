package com.basarili.baslangisc.game.dataStore

import android.content.SharedPreferences

class DataStore(private val sharedPreferences: SharedPreferences) {

    private val KEY_IS_TUTORIAL = "is_tutorial"
    private val KEY_GAZ         = "gaz"
    private val KEY_COIN        = "coin"
    private val KEY_LVL         = "lvl"
    private val KEY_ITEM_COUNT  = "item_count"

    var isTutorial = sharedPreferences.getBoolean(KEY_IS_TUTORIAL, true)
        private set
    var gaz = sharedPreferences.getInt(KEY_GAZ, 0)
        private set
    var coin = sharedPreferences.getInt(KEY_COIN, 0)
        private set
    var lvl = sharedPreferences.getInt(KEY_LVL, 1)
        private set

    fun getItemCountByIndex(index: Int) = sharedPreferences.getInt(KEY_ITEM_COUNT + index, 0)

    fun updateIsTutorial(block: (Boolean) -> Boolean) {
        isTutorial = block(isTutorial)
        sharedPreferences.edit().putBoolean(KEY_IS_TUTORIAL, isTutorial).apply()
    }
    fun updateGaz(block: (Int) -> Int) {
        gaz = block(gaz)
        sharedPreferences.edit().putInt(KEY_GAZ, gaz).apply()
    }
    fun updateCoin(block: (Int) -> Int) {
        coin = block(coin)
        sharedPreferences.edit().putInt(KEY_COIN, coin).apply()
    }
    fun updateLVL(block: (Int) -> Int) {
        lvl = block(lvl)
        sharedPreferences.edit().putInt(KEY_LVL, lvl).apply()
    }
    fun updateItemCountByIndex(index: Int, block: (Int) -> Int) {
        val itemCount = block(getItemCountByIndex(index))
        sharedPreferences.edit().putInt(KEY_ITEM_COUNT + index, itemCount).apply()
    }

}