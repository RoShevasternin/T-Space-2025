package com.liberator.wisoliter.game.actors.main

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.liberator.wisoliter.game.actors.ABalance
import com.liberator.wisoliter.game.actors.dialog.ADialogArmy
import com.liberator.wisoliter.game.actors.dialog.ADialogAten_Neobhodimo
import com.liberator.wisoliter.game.actors.dialog.ADialogAten_Pozdravly
import com.liberator.wisoliter.game.actors.dialog.ADialogAten_Zakonchilis
import com.liberator.wisoliter.game.actors.dialog.ADialogMagaz
import com.liberator.wisoliter.game.actors.game.AAtack
import com.liberator.wisoliter.game.actors.game.ACreate
import com.liberator.wisoliter.game.actors.world.ACountry
import com.liberator.wisoliter.game.actors.world.AWorldScrollable
import com.liberator.wisoliter.game.utils.*
import com.liberator.wisoliter.game.utils.actor.*
import com.liberator.wisoliter.game.utils.advanced.AdvancedGroup
import com.liberator.wisoliter.game.utils.advanced.AdvancedScreen
import com.liberator.wisoliter.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainGame(
    override val screen: AdvancedScreen,
    val aWorld         : AWorldScrollable,
    val aDialogArmy    : ADialogArmy,
    val aDialogMagaz   : ADialogMagaz,

    val adialogaten_Neobhodimo : ADialogAten_Neobhodimo,
    val adialogaten_Zakonchilis: ADialogAten_Zakonchilis,
    val adialogaten_Pozdravly  : ADialogAten_Pozdravly,
): AdvancedGroup() {

    private val aBalance = ABalance(screen)
    private val aCreate  = ACreate(screen)
    private val aAtack   = AAtack(screen)

    // Field
    private var selectedCountry: ACountry? = null

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f
                aWorld.scrollToPos()

                addBalance()
                addCreate()
                addAtack()

                aDialogArmy.blockX  = { hideDialog(aDialogArmy) }
                aDialogMagaz.blockX = { hideDialog(aDialogMagaz) }

                adialogaten_Neobhodimo.blockX  = { completeAtack(adialogaten_Neobhodimo) }
                adialogaten_Zakonchilis.blockX = { completeAtack(adialogaten_Zakonchilis) }
                adialogaten_Pozdravly.blockX   = { completeAtack(adialogaten_Pozdravly) }

                adialogaten_Zakonchilis.blockStart = { completeAtack(adialogaten_Zakonchilis) }
                adialogaten_Pozdravly.blockStart   = { completeAtack(adialogaten_Pozdravly) }

                handlerWorld()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addBalance() {
        addActor(aBalance)
        aBalance.setBounds(27f, HEIGHT_UI, 724f, 174f)
    }

    private fun addCreate() {
        addActor(aCreate)
        aCreate.setBounds(138f, -340f, 503f, 332f)

        aCreate.blockArmy  = { showDialog(aDialogArmy) }
        aCreate.blockMagaz = { showDialog(aDialogMagaz) }
    }

    private fun addAtack() {
        addActor(aAtack)
        aAtack.setBounds(140f, -120f, 498f, 113f)
        aAtack.disable()

        aAtack.blockAtack = {
            selectedCountry?.atack() ?: log("Atack Fail: no select COUNTRY")
        }
        aAtack.blockNoXP = {
            showDialogAten(adialogaten_Zakonchilis)
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        withContext(Dispatchers.Default) {
            withContext(Dispatchers.Default) {
                launch { animShowSuspend(TIME_ANIM_SCREEN) }
                launch { aWorld.animShowSuspend(TIME_ANIM_SCREEN) }
            }
            delay(700)
            withContext(Dispatchers.Default) {
                launch { aBalance.animMoveToSuspend(27f, 1480f, TIME_ANIM_SCREEN, Interpolation.sineOut) }
                launch { aCreate.animMoveToSuspend(138f, 38f, TIME_ANIM_SCREEN, Interpolation.sineOut) }
            }
        }
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

    // Anim ------------------------------------------------

    private fun showDialog(dialog: AdvancedGroup) {
        dialog.apply {
            clearActions()
            addAction(Actions.moveTo(this.x, 0f, TIME_ANIM_SCREEN, Interpolation.sineOut))
        }
    }

    private fun hideDialog(dialog: AdvancedGroup) {
        dialog.apply {
            clearActions()
            addAction(Actions.moveTo(this.x, -this.height, TIME_ANIM_SCREEN, Interpolation.sineIn))
        }
    }

    private fun animToAtack() {
        aCreate.addAction(Actions.moveTo(138f, -350f, TIME_ANIM_SCREEN, Interpolation.sineIn))
        aAtack.addAction(Actions.moveTo(140f, 70f, TIME_ANIM_SCREEN, Interpolation.sineIn))
    }

    private fun animToCreate() {
        aCreate.addAction(Actions.moveTo(138f, 38f, TIME_ANIM_SCREEN, Interpolation.sineIn))
        aAtack.addAction(Actions.moveTo(140f, -120f, TIME_ANIM_SCREEN, Interpolation.sineIn))
    }

    private fun showDialogAten(dialog: AdvancedGroup) {
        screen.apply {
            stageBack.root.disable()
            stageUI.root.disable()
            topStageUI.root.disable()
        }
        dialog.animShow(TIME_ANIM_SCREEN) {
            dialog.enable()
        }
    }

    private fun hideDialogAten(dialog: AdvancedGroup) {
        dialog.animHide(TIME_ANIM_SCREEN) {
            dialog.disable()

            screen.apply {
                stageBack.root.enable()
                stageUI.root.enable()
                topStageUI.root.enable()
            }
        }
    }

    // Logic ------------------------------------------------

    private fun handlerWorld() {
        aWorld.apply {
            blockSelectCountry = { selectedCountry ->

                if (selectedCountry.uron < selectedCountry.xp) {
                    animToAtack()

                    if (selectedCountry != this@AMainGame.selectedCountry) {
                        adialogaten_Neobhodimo.setNeedXp(selectedCountry.xp - selectedCountry.uron)
                        adialogaten_Neobhodimo.blockStart = {
                            aAtack.enable()
                            selectedCountry.blockZahvacheno = { showDialogAten(adialogaten_Pozdravly) }
                            this@AMainGame.selectedCountry = selectedCountry
                            hideDialogAten(adialogaten_Neobhodimo)
                        }
                        showDialogAten(adialogaten_Neobhodimo)
                    }

                }

            }
        }
    }

    private fun completeAtack(dialog: AdvancedGroup) {
        aAtack.disable()
        selectedCountry = null
        hideDialogAten(dialog)
        animToCreate()
    }

}