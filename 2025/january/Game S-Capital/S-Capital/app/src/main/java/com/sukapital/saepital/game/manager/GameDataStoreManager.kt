package com.sukapital.saepital.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sukapital.saepital.appContext
import kotlinx.coroutines.flow.first

object GameDataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "GLOBAL_DATA")


    object DesireList: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("DesireList")
    }
    object IncomeList: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("IncomeList")
    }
    object ExpenseList: AbstractDataStore.DataStoreElement<String>() {
        override val key = stringPreferencesKey("ExpenseList")
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

