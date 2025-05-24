package com.tinfenker.capitalnoestroy.game.dataStore

import com.tinfenker.capitalnoestroy.game.data.Contribution
import com.tinfenker.capitalnoestroy.game.manager.GameDataStoreManager
import com.tinfenker.capitalnoestroy.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ContributionUtil(val coroutine: CoroutineScope) {

    val contributionListFlow = MutableStateFlow(mutableListOf<Contribution>())

    init {
        coroutine.launch {
            val contributionListStr = GameDataStoreManager.ContributionList.get()
            if (contributionListStr != null) contributionListFlow.value = Json.decodeFromString<MutableList<Contribution>>(contributionListStr)

            log("Store Contribution:")
            contributionListFlow.value.onEachIndexed { index, contribution ->
                log("$index: $contribution")
            }
        }

    }

    fun update(block: (MutableList<Contribution>) -> MutableList<Contribution>) {
        coroutine.launch {
            contributionListFlow.value = block(contributionListFlow.value)

            log("Store Contribution update:")
            contributionListFlow.value.onEachIndexed { index, contribution ->
                log("$index: $contribution")
            }
            GameDataStoreManager.ContributionList.update { Json.encodeToString<List<Contribution>>(contributionListFlow.value) }
        }
    }

}