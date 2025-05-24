package com.smarteca.foundsender.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.smarteca.foundsender.game.actors.button.AButton
import com.smarteca.foundsender.game.manager.PreferencesManager
import com.smarteca.foundsender.game.screens.AccessScreen
import com.smarteca.foundsender.game.utils.GameColor
import com.smarteca.foundsender.game.utils.PREF_KEY_ACCESS
import com.smarteca.foundsender.game.utils.TIME_ANIM_SCREEN
import com.smarteca.foundsender.game.utils.actor.animDelay
import com.smarteca.foundsender.game.utils.actor.animHide
import com.smarteca.foundsender.game.utils.actor.animShow
import com.smarteca.foundsender.game.utils.actor.disable
import com.smarteca.foundsender.game.utils.advanced.AdvancedMainGroup
import com.smarteca.foundsender.game.utils.font.FontParameter
import com.smarteca.foundsender.game.utils.gdxGame
import com.smarteca.foundsender.util.AppsFlyerManager
import com.smarteca.foundsender.util.permission.PermissionATT

class AMainAccess(
    override val screen: AccessScreen,
): AdvancedMainGroup() {

    private val parameter = FontParameter()
        .setCharacters(FontParameter.CharType.ALL)
        .setSize(72)

    private val font = screen.fontGenerator_Bold.generateFont(parameter)
    private val ls   = LabelStyle(font, GameColor.green)

    private val imgAccess = Image(gdxGame.assetsAccess.ACCESS)
    private val imgCancel = Image(gdxGame.assetsAccess.you_can_cancel)
    private val btnAllow  = Image(gdxGame.assetsAccess.allow_def)
    private val btnAskNot = Image(gdxGame.assetsAccess.ask_not_def)

    private val lblText = Label("Test Text", ls)

    //var blockPurchase: () -> Unit = {}

    override fun addActorsOnGroup() {
        disable()
        color.a = 0f
        addImgAccess()
        addImgCancel()

        //addActor(lblText)
        //lblText.setBounds(454f, 1217f, 273f, 124f)
        //lblText.setAlignment(Align.center)
        //lblText.setText("Access: ${PreferencesManager.getString(PREF_KEY_ACCESS, "null")}")

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgAccess() {
        addActor(imgAccess)
        imgAccess.setBounds(66f, 1600f, 1048f, 778f)

        addActors(btnAllow, btnAskNot)
        btnAllow.apply {
            setBounds(391f, 1803f, 396f, 67f)
//            setOnClickListener {
//                PreferencesManager.saveString(PREF_KEY_ACCESS, Access.Yes.name)
//                lblText.setText("Access: ${PreferencesManager.getString(PREF_KEY_ACCESS)}")
//            }
        }
        btnAskNot.apply {
            setBounds(327f, 1645f, 525f, 66f)
//            setOnClickListener {
//                PreferencesManager.saveString(PREF_KEY_ACCESS, Access.Not.name)
//                lblText.setText("Access: ${PreferencesManager.getString(PREF_KEY_ACCESS)}")
//            }
        }
    }

    private fun addImgCancel() {
        addActor(imgCancel)
        imgCancel.setBounds(138f, 180f, 904f, 66f)
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Runnable) {
        //children.onEach { it.clearActions() }
        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.run() }
    }

    override fun animHideMain(blockEnd: Runnable) {
        //children.onEach { it.clearActions() }
        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.run() }
    }

    enum class Access {
        Yes, Not
    }

}
