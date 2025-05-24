package com.smarteca.foundsender.game.manager

import org.robovm.apple.foundation.*

object PreferencesManager {

    private val userDefaults = NSUserDefaults.getStandardUserDefaults()

    // 🔹 Збереження String
    fun saveString(key: String, value: String) {
        userDefaults.put(key, NSString(value))
        userDefaults.synchronize()
    }

    // 🔹 Отримання String
    fun getString(key: String, defaultValue: String = ""): String {
        return if (userDefaults.get(key) != null) userDefaults.getString(key) else defaultValue
    }

    // 🔹 Збереження Int
    fun saveInt(key: String, value: Int) {
        userDefaults.put(key, value)
        userDefaults.synchronize()
    }

    // 🔹 Отримання Int
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return if (userDefaults.get(key) != null) userDefaults.getInt(key) else defaultValue
    }

    // 🔹 Збереження Long
    fun saveLong(key: String, value: Long) {
        userDefaults.put(key, value)
        userDefaults.synchronize()
    }

    // 🔹 Отримання Long
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return if (userDefaults.get(key) != null) userDefaults.getLong(key) else defaultValue
    }

    // 🔹 Збереження Boolean
    fun saveBoolean(key: String, value: Boolean) {
        userDefaults.put(key, value)
        userDefaults.synchronize()
    }

    // 🔹 Отримання Boolean
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return if (userDefaults.get(key) != null) userDefaults.getBoolean(key) else defaultValue
    }

    // 🔹 Збереження Float
    fun saveFloat(key: String, value: Float) {
        userDefaults.put(key, value)
        userDefaults.synchronize()
    }

    // 🔹 Отримання Float
    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return if (userDefaults.get(key) != null) userDefaults.getFloat(key) else defaultValue
    }

    // 🔹 Збереження Double
    fun saveDouble(key: String, value: Double) {
        userDefaults.put(key, value)
        userDefaults.synchronize()
    }

    // 🔹 Отримання Double
    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return if (userDefaults.get(key) != null) userDefaults.getDouble(key) else defaultValue
    }

    // 🔹 Видалення окремого значення
    fun removeValue(key: String) {
        userDefaults.remove(key)
        userDefaults.synchronize()
    }

    // 🔹 Очистити всі дані
    fun clearAll() {
        val keys = userDefaults.asDictionary().keys
        for (key in keys) {
            userDefaults.remove(key.toString())
        }
        userDefaults.synchronize()
    }

}
