package com.inveanst.litka.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.inveanst.litka.game.LibGDXGame
import com.inveanst.litka.game.actors.ABackground
import com.inveanst.litka.game.actors.AButton
import com.inveanst.litka.game.actors.TmpGroup
import com.inveanst.litka.game.actors.layout.AVerticalGroup
import com.inveanst.litka.game.actors.layout.Layout
import com.inveanst.litka.game.utils.TIME_ANIM
import com.inveanst.litka.game.utils.actor.animHide
import com.inveanst.litka.game.utils.actor.animShow
import com.inveanst.litka.game.utils.actor.setOnClickListener
import com.inveanst.litka.game.utils.advanced.AdvancedGroup
import com.inveanst.litka.game.utils.advanced.AdvancedScreen
import com.inveanst.litka.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MenuScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var selectedTestType = TestType.OI
            private set
    }

    enum class TestType {
        OI, AO, RD
    }

    private val tmpGroup      = TmpGroup(this)
    private val imgBackground = ABackground(this)

    private val imgSelectTema = Image(game.all.select_tema)

    private val btnOI = AButton(this, AButton.Static.Type.OI)
    private val btnAO = AButton(this, AButton.Static.Type.AO)
    private val btnRD = AButton(this, AButton.Static.Type.RD)

    private val verticalGroup = AVerticalGroup(this,21f,
        alignmentH = Layout.AlignmentHorizontal.CENTER
    )

    override fun AdvancedStage.addActorsOnStageUI() {
        addAndFillActors(imgBackground, tmpGroup)
        tmpGroup.apply {
            color.a = 0f
            addImgSelectTema()
            addVerticalGroup()
            animShow(TIME_ANIM)
        }
    }

    private fun AdvancedGroup.addImgSelectTema() {
        addActor(imgSelectTema)
        imgSelectTema.setBounds(28f, 400f, 319f, 245f)
    }

    private fun AdvancedGroup.addVerticalGroup() {
        addActor(verticalGroup)
        verticalGroup.setBounds(73f, 86f, 229f, 293f)
        verticalGroup.addBtns()
    }

    private fun AVerticalGroup.addBtns() {
        btnOI.apply {
            setSize(229f,101f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    selectedTestType = TestType.OI
                    game.navigationManager.navigate(SelectedTestScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnAO.apply {
            setSize(228f,75f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    selectedTestType = TestType.AO
                    game.navigationManager.navigate(SelectedTestScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        btnRD.apply {
            setSize(228f,75f)
            setOnClickListener {
                tmpGroup.animHide(TIME_ANIM) {
                    selectedTestType = TestType.RD
                    game.navigationManager.navigate(SelectedTestScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
        addActors(btnOI, btnAO, btnRD)
    }

}