package com.liberator.wisoliter.game.actors.game

import com.liberator.wisoliter.game.actors.button.AButton
import com.liberator.wisoliter.game.dataStore.DataItem
import com.liberator.wisoliter.game.dataStore.DataItemType
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.game.utils.gdxGame
import com.liberator.wisoliter.util.log

class AAtack(override val screen: AdvancedScreen): AdvancedGroup() {

    private val btnAtack  = AButton(screen, AButton.Type.Atak)
    private val aCoinMinus = ACoinMinus(screen)

    var blockAtack = {}
    var blockNoXP  = {}

    override fun addActorsOnGroup() {
        addBtnAtack()
        addCoinMinus()
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnAtack() {
        addAndFillActor(btnAtack)
        btnAtack.setOnClickListener(null) {
            if (gdxGame.ds_XP.flow.value >= 10) {
                var randomSoundIndex = (0..14).random()
                log("atack SOUND $randomSoundIndex")
                gdxGame.soundUtil.apply { play(listAtack[randomSoundIndex]) }

                gdxGame.ds_XP.update { it - 10 }

                gdxGame.ds_DataItem.update { mainList ->
                    // Знаходимо перший непорожній список разом із його індексом
                    val index = mainList.indexOfFirst { it.isNotEmpty() }
                    if (index == -1) return@update mainList // Якщо всі списки порожні, повертаємо оригінальний список

                    // Оновлюємо перший непорожній список
                    val updatedList = mainList[index].toMutableList().apply {
                        val firstItem = first()
                        firstItem.xp -= 10
                        if (firstItem.xp <= 0) remove(firstItem)
                    }

                    // Повертаємо оновлений головний список
                    mainList.toMutableList().apply {
                        this[index] = updatedList
                    }
                }

                aCoinMinus.animMinus()
                blockAtack()
            } else blockNoXP()
        }
    }

    private fun addCoinMinus() {
        addActor(aCoinMinus)
        aCoinMinus.setBounds(-123f, 36f, 744f, 192f)
    }

}