package com.rayscaya.nasjajdenye.game.actors

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.rayscaya.nasjajdenye.game.actors.button.AButton
import com.rayscaya.nasjajdenye.game.data.DataInput
import com.rayscaya.nasjajdenye.game.utils.TIME_ANIM_SCREEN
import com.rayscaya.nasjajdenye.game.utils.actor.animHide
import com.rayscaya.nasjajdenye.game.utils.actor.animMoveTo
import com.rayscaya.nasjajdenye.game.utils.actor.animShow
import com.rayscaya.nasjajdenye.game.utils.actor.disable
import com.rayscaya.nasjajdenye.game.utils.addMonthsToToday
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedGroup
import com.rayscaya.nasjajdenye.game.utils.advanced.AdvancedScreen
import com.rayscaya.nasjajdenye.game.utils.gdxGame
import com.rayscaya.nasjajdenye.game.utils.toSeparateWithSymbol
import com.rayscaya.nasjajdenye.util.log
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

class APaneltem(
    override val screen: AdvancedScreen,
    val dataInput: DataInput,
    val ls29: Label.LabelStyle,
    val ls39: Label.LabelStyle,
): AdvancedGroup() {

    private val summaPlus: Int = (dataInput.summa * (dataInput.percent / 100f)).roundToInt()

    private val imgMain    = Image(gdxGame.assetsAll.PANEL)

    private val tmpGroup1  = ATmpGroup(screen)
    private val lblTitle   = Label(dataInput.title, ls39)
    private val lblSumma   = Label(dataInput.summa.toSeparateWithSymbol(' ') + " ₽", ls39)
    private val lblDo      = Label(addMonthsToToday(dataInput.period), ls39)
    private val lblPercent = Label("${dataInput.percent}%", ls39)
    private val lblPlus    = Label(summaPlus.toSeparateWithSymbol(' ') + " ₽", ls39)

    private val tmpGroup2    = ATmpGroup(screen)
    private val lblT_Summa   = Label("Сумма", ls29)
    private val lblT_Do      = Label("До", ls29)
    private val lblT_Percent = Label("Ставка", ls29)
    private val lblT_Plus    = Label("Прибыль", ls29)

    private val btnEdit   = AButton(screen, AButton.Type.Edit)
    private val btnDelete = AButton(screen, AButton.Type.Delete)

    private val nX_2_DELETE = -135f
    private val nX_1_EDIT   = 135f

    var blockDelete: (DataInput) -> Unit = {}
    var blockEdit  : (DataInput) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgMain)

        addAndFillActors(tmpGroup1, tmpGroup2)
        tmpGroup1.addLbls1()
        tmpGroup2.addLbls2()

        addBtns()

        children.onEach { it.disable() }

        addListener(getListener())
    }

    // Actors --------------------------------------------------

    private fun AdvancedGroup.addLbls1() {
        addActors(lblTitle, lblSumma, lblDo, lblPercent, lblPlus)
        lblTitle.apply {
            setBounds(50f, 311f, 356f, 50f)
            setAlignment(Align.center)
        }
        lblSumma.apply {
            setBounds(222f, 199f, 185f, 50f)
            setAlignment(Align.right)
        }
        lblDo.apply {
            setBounds(234f, 112f, 173f, 50f)
            setAlignment(Align.right)
        }
        lblPercent.apply {
            setBounds(341f, 25f, 66f, 50f)
            setAlignment(Align.right)
        }

        addActors(lblT_Summa, lblT_Do, lblT_Percent)
        lblT_Summa.apply {
            setBounds(50f, 199f, 108f, 50f)
            setAlignment(Align.left)
        }
        lblT_Do.apply {
            setBounds(50f, 112f, 108f, 50f)
            setAlignment(Align.left)
        }
        lblT_Percent.apply {
            setBounds(50f, 25f, 108f, 50f)
            setAlignment(Align.left)
        }
    }

    private fun AdvancedGroup.addLbls2() {
        addActors(lblPlus, lblT_Plus)
        lblPlus.apply {
            setBounds(614f, 286f, 165f, 50f)
            setAlignment(Align.right)
        }

        lblT_Plus.apply {
            setBounds(614f, 336f, 136f, 50f)
            setAlignment(Align.left)
        }
    }

    private fun addBtns() {
        addActors(btnEdit, btnDelete)
        btnEdit.apply {
            setBounds(0f, 0f, 136f, 411f)
            disable()
            color.a = 0f
            setOnClickListener { blockEdit(dataInput) }
        }

        btnDelete.apply {
            setBounds(694f, 0f, 136f, 411f)
            disable()
            color.a = 0f
            setOnClickListener(gdxGame.soundUtil.delete) { blockDelete(dataInput) }
        }
    }

    // Logic ----------------------------------------------------

    private var startX = 0f
    private var isEdit   = false
    private var isDelete = false
    private var isDragged = false

    private fun getListener() = object : InputListener() {

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            startX = x
            isDragged = false
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            if (isDragged) return

            val deltaX = x - startX

            log("de = $deltaX")
            if(deltaX > 25) {
                isDragged = true

                if (isDelete) {
                    isDelete = false
                    animHideBtnDelete()
                    event?.cancel()
                } else {
                    isDelete = false
                    isEdit   = true
                    animShowBtnEdit()
                    event?.cancel()
                }
            }

            if(deltaX < -25) {
                isDragged = true

                if (isEdit) {
                    isEdit = false
                    animHideBtnEdit()
                    event?.cancel()
                } else {
                    isEdit   = false
                    isDelete = true
                    animShowBtnDelete()
                    event?.cancel()
                }
            }

        }
    }

    private fun animShowBtnEdit() {
        btnEdit.animShow(TIME_ANIM_SCREEN)
        btnEdit.enable()

        tmpGroup1.animMoveTo(nX_1_EDIT, 0f, TIME_ANIM_SCREEN)
    }

    private fun animHideBtnEdit() {
        btnEdit.animHide(TIME_ANIM_SCREEN)
        btnEdit.disable()

        tmpGroup1.animMoveTo(0f, 0f, TIME_ANIM_SCREEN)
    }

    private fun animShowBtnDelete() {
        btnDelete.animShow(TIME_ANIM_SCREEN)
        btnDelete.enable()

        tmpGroup2.animMoveTo(nX_2_DELETE, 0f, TIME_ANIM_SCREEN)
    }

    private fun animHideBtnDelete() {
        btnDelete.animHide(TIME_ANIM_SCREEN)
        btnDelete.disable()

        tmpGroup2.animMoveTo(0f, 0f, TIME_ANIM_SCREEN)
    }

}