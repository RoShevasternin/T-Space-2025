package com.gpecspertisa.sollomka.game.dataStore

import android.content.SharedPreferences

class DataStore(private val sharedPreferences: SharedPreferences) {

    private val KEY_BALANCE = "key_balance"
    private val KEY_LVL_2   = "key_lvl_2"
    private val KEY_LVL_3   = "key_lvl_3"
    private val KEY_LVL_4   = "key_lvl_4"

    var balance = sharedPreferences.getInt(KEY_BALANCE, 0)
        private set
    var level_2 = sharedPreferences.getBoolean(KEY_LVL_2, false)
        private set
    var level_3 = sharedPreferences.getBoolean(KEY_LVL_3, false)
        private set
    var level_4 = sharedPreferences.getBoolean(KEY_LVL_4, false)
        private set

    fun updateBalance(block: (Int) -> Int) {
        balance = block(balance)
        sharedPreferences.edit().putInt(KEY_BALANCE, balance).apply()
    }
    fun updateLVL_2(block: (Boolean) -> Boolean) {
        level_2 = block(level_2)
        sharedPreferences.edit().putBoolean(KEY_LVL_2, level_2).apply()
    }
    fun updateLVL_3(block: (Boolean) -> Boolean) {
        level_3 = block(level_3)
        sharedPreferences.edit().putBoolean(KEY_LVL_3, level_3).apply()
    }
    fun updateLVL_4(block: (Boolean) -> Boolean) {
        level_4 = block(level_4)
        sharedPreferences.edit().putBoolean(KEY_LVL_4, level_4).apply()
    }

}