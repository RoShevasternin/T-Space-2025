package com.pezdunkov.sberdarorcassa.game.actors.main

import com.badlogic.gdx.Gdx
import com.pezdunkov.sberdarorcassa.game.actors.ABtns
import com.pezdunkov.sberdarorcassa.game.actors.AInputs
import com.pezdunkov.sberdarorcassa.game.actors.AScrollPane
import com.pezdunkov.sberdarorcassa.game.actors.autoLayout.AVerticalGroup
import com.pezdunkov.sberdarorcassa.game.data.DataItem
import com.pezdunkov.sberdarorcassa.game.screens.AddTovarScreen
import com.pezdunkov.sberdarorcassa.game.utils.Block
import com.pezdunkov.sberdarorcassa.game.utils.advanced.AdvancedMainGroup
import com.pezdunkov.sberdarorcassa.game.utils.gdxGame
import com.pezdunkov.sberdarorcassa.game.utils.isNumeric
import java.text.SimpleDateFormat
import java.util.*

class AMainAddTovar(override val screen: AddTovarScreen): AdvancedMainGroup() {

    private val aVerticalGroup = AVerticalGroup(screen, 46f, isWrap = true)
    private val aScroll        = AScrollPane(aVerticalGroup)

    private val aInputs = AInputs(screen)
    private val aBtns   = ABtns(screen)

    // field
    private var inputDataItem = DataItem()

    override fun addActorsOnGroup() {
        //screen.topStageBack.root.color.a = 0f
        //color.a = 0f

        addScroll()

        //animShowMain()

    }

    override fun dispose() {
        Gdx.input.setOnscreenKeyboardVisible(false)
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addScroll() {
        addActor(aScroll)
        aScroll.setBounds(37f, 0f, 636f, 1082f)

        aVerticalGroup.setSize(636f, 1082f)

        aInputs.setSize(636f, 1000f)
        aVerticalGroup.addActor(aInputs)

        aBtns.setSize(636f, 124f)
        aVerticalGroup.addActor(aBtns)

        handlerBtns()
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.topStageBack.root.animShow(TIME_ANIM_SCREEN)
//
//        this.animShow(TIME_ANIM_SCREEN)
//        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
        blockEnd.invoke()
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.topStageBack.root.animHide(TIME_ANIM_SCREEN)
//
//        this.animHide(TIME_ANIM_SCREEN)
//        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
        blockEnd.invoke()
    }

    // Logic -----------------------------------------------

    private fun checkAndSaveDataItem(): Boolean {
        inputDataItem.isProdano     = false
        inputDataItem.nName         = aInputs.listInputs[0].text.toString()
        inputDataItem.date          = getFormattedDate()
        inputDataItem.kilkist       = aInputs.listInputs[1].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.priceZakupu1  = aInputs.listInputs[2].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.priceProdaja1 = aInputs.listInputs[3].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.nalog         = aInputs.listInputs[4].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }
        inputDataItem.marja         = aInputs.listInputs[5].text.toString().let { if (it.isNumeric()) it.toInt() else 0 }

        if (
            inputDataItem.nName.isNotEmpty() &&
            inputDataItem.kilkist        > 0 &&
            inputDataItem.priceZakupu1   > 0 &&
            inputDataItem.priceProdaja1  > 0 &&
            inputDataItem.nalog          > 0 &&
            inputDataItem.marja          > 0
        ) {
            gdxGame.ds_ItemData.update {
                val mList = it.toMutableList()
                mList.add(inputDataItem)
                mList
            }
            return true
        } else return false
    }

    fun getFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun handlerBtns() {
        aBtns.blockSave = {
            if (checkAndSaveDataItem()) screen.hideScreen {
                gdxGame.navigationManager.back()
            } else {
                gdxGame.soundUtil.apply { play(fail) }
            }
        }
    }

}