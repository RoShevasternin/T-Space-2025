package com.ingiodin.strinvestida.game.screens.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.ingiodin.strinvestida.game.actors.AButton
import com.ingiodin.strinvestida.game.utils.GColor
import com.ingiodin.strinvestida.game.utils.TIME_ANIM
import com.ingiodin.strinvestida.game.utils.actor.animHide
import com.ingiodin.strinvestida.game.utils.actor.animShow
import com.ingiodin.strinvestida.game.utils.actor.setOnClickListener
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedScreen
import com.ingiodin.strinvestida.game.utils.advanced.AdvancedStage
import com.ingiodin.strinvestida.game.utils.font.FontParameter
import com.ingiodin.strinvestida.game.utils.region

abstract class AbstractMainScreen : AdvancedScreen() {

    abstract val title: String
    abstract val text : String
    abstract val screenName : String

    private val fontParameter = FontParameter()
    private val fontM_54 = fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC).setSize(54))
    private val fontR_37 = fontGenerator_Roboto_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.CYRILLIC).setSize(37))

    private val ls54 = LabelStyle(fontM_54, Color.BLACK)
    private val ls37 = LabelStyle(fontR_37, GColor.text)

    private val imgLogo    by lazy { Image(game.loader.logo) }
    private val btnBack    by lazy { AButton(this, AButton.Type.Back) }
    private val btnNext    by lazy { AButton(this, AButton.Type.Next) }
    private val lblTitle   by lazy { Label(title, ls54) }

    protected val imgWhite by lazy { Image(game.all.white) }
    protected val lblText  by lazy { Label(text, ls37) }

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.loader.B_Loader.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addActors(imgLogo, imgWhite, lblTitle, lblText, btnBack, btnNext)
        imgLogo.setBounds(245f,1966f,521f,101f)
        lblTitle.setBounds(0f,1340f,1014f,165f)
        lblTitle.setAlignment(Align.center)
        lblText.setAlignment(Align.center)
        lblTitle.wrap = true
        lblText.wrap = true
        btnBack.apply {
            setBounds(54f, 237f, 109f, 109f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
        btnNext.apply {
            setBounds(851f, 237f, 109f, 109f)
            setOnClickListener(game.soundUtil) {
                root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(screenName, this::class.java.name)
                }
            }
        }

        addToStage()
    }

    abstract fun AdvancedStage.addToStage()

}