package com.proccaptald.proffesionalestic.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.proccaptald.proffesionalestic.R
import com.proccaptald.proffesionalestic.appContext
import com.proccaptald.proffesionalestic.game.LibGDXGame
import com.proccaptald.proffesionalestic.game.actors.AButton
import com.proccaptald.proffesionalestic.game.utils.TIME_ANIM
import com.proccaptald.proffesionalestic.game.utils.actor.animHide
import com.proccaptald.proffesionalestic.game.utils.actor.animShow
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedScreen
import com.proccaptald.proffesionalestic.game.utils.advanced.AdvancedStage
import com.proccaptald.proffesionalestic.game.utils.font.FontParameter
import com.proccaptald.proffesionalestic.game.utils.region

class WinnerScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.CYRILLIC_ALL).setSize(59)
    private val font          = fontGenerator_Pusia_Bold.generateFont(fontParameter)

    private val ls59 = LabelStyle(font, Color.WHITE)

    private val imgPanel   = Image(game.all.winner)
    private val btnRestart = AButton(this, AButton.Static.Type.Restart)
    private val btnExit    = AButton(this, AButton.Static.Type.Exit)
    private val imgIcon    = Image(game.all.listPersIcon[GameScreen.WINNER_INDEX])
    private val imgCoin    = Image(game.all.coin)
    private val lblCount  = Label(GameScreen.WINNER_COIN.toString(), ls59)
    private val lblProf   = Label(appContext.resources.getStringArray(R.array.professions)[GameScreen.WINNER_INDEX], ls59)

    override fun show() {
        stageUI.root.animHide()
        setBackBackground(game.splash.BACKGROUND.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM) {
            game.soundUtil.apply { play(winner, 0.35f) }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addImgPanel()
        addBtns()
        addImgCoinIcon()

        addActors(lblCount, lblProf)
        lblCount.setBounds(313f,611f,98f,41f)
        lblProf.setBounds(177f,678f,302f,71f)
        lblProf.setAlignment(Align.center)
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(161f,1052f,333f,63f)
    }

    private fun AdvancedStage.addImgCoinIcon() {
        addActors(imgIcon, imgCoin)
        imgIcon.setBounds(199f,750f,258f,258f)
        imgCoin.setBounds(246f,600f,56f,61f)

        imgIcon.setOrigin(Align.center)
        imgIcon.addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.scaleBy(-0.25f, -0.25f, 0.5f, Interpolation.sine),
                    Actions.scaleBy(0.25f, 0.25f, 0.5f, Interpolation.sine),
                )
            ))
    }

    private fun AdvancedStage.addBtns() {
        addActors(btnExit, btnRestart)

        btnExit.apply {
            setBounds(159f,374f,126f,132f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.back()
                }
            }
        }
        btnRestart.apply {
            setBounds(372f,374f,126f,132f)
            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(GameScreen::class.java.name)
                }
            }
        }
    }

}