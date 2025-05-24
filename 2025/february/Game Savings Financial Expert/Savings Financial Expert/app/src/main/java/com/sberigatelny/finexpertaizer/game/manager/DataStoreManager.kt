package com.sberigatelny.finexpertaizer.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sberigatelny.finexpertaizer.appContext
import kotlinx.coroutines.flow.first

object DataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DATA_STORE")


    object Balance: DataStoreElement<Int>() {
        override val key = intPreferencesKey("balance")
    }

    object IsTutorial: DataStoreElement<Boolean>() {
        override val key = booleanPreferencesKey("is_tutorial")
    }

    object IsNewWork: DataStoreElement<Boolean>() {
        override val key = booleanPreferencesKey("is_newwork")
    }

    object IsPereezd: DataStoreElement<Boolean>() {
        override val key = booleanPreferencesKey("is_pereezd")
    }

    object DataPercent: DataStoreElement<String>() {
        override val key = stringPreferencesKey("DataPercent")
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

