package com.bagazkz.klarebadew.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bagazkz.klarebadew.appContext
import kotlinx.coroutines.flow.first

object DataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DATA_STORE")


    object XP: DataStoreElement<Int>() {
        override val key = intPreferencesKey("xp")
    }

    object Gold: DataStoreElement<Int>() {
        override val key = intPreferencesKey("gold")
    }

    object Gaz: DataStoreElement<Int>() {
        override val key = intPreferencesKey("gaz")
    }

}

abstract class AbstractDataStore {
    abstract val Context.dataStore: DataStore<Preferences>

    abstract inner class DataStoreElement<T> {
        abstract val key: Preferences.Key<T>

        open suspend fun collect(block: suspend (T?) -> Unit) {
            appContext.dataStore.data.collect { block(it[key]) }
        }

        open suspend fun update(block: suspend (T?) -> T) {
            appContext.dataStore.edit { it[key] = block(it[key]) }
        }

        open suspend fun get(): T? {
            return appContext.dataStore.data.first()[key]
        }
    }
}

