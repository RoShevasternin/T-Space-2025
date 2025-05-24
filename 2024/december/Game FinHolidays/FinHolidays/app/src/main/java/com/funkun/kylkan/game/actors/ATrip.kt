package com.funkun.kylkan.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.funkun.kylkan.game.actors.progress.AProgress
import com.funkun.kylkan.game.dataStore.TripData
import com.funkun.kylkan.game.screens.AddTripsScreen
import com.funkun.kylkan.game.screens.TripsOpenedScreen
import com.funkun.kylkan.game.utils.actor.disable
import com.funkun.kylkan.game.utils.actor.setBounds
import com.funkun.kylkan.game.utils.actor.setOnClickListener
import com.funkun.kylkan.game.utils.actor.setOnTouchListener
import com.funkun.kylkan.game.utils.advanced.AdvancedGroup
import com.funkun.kylkan.game.utils.advanced.AdvancedScreen
import com.funkun.kylkan.game.utils.gdxGame
import com.funkun.kylkan.game.utils.toSeparate

class ATrip(
    override val screen: AdvancedScreen,
    val tripData: TripData,
    val ls54: Label.LabelStyle,
    val ls37: Label.LabelStyle,
    val ls48: Label.LabelStyle,
): AdvancedGroup() {

    private val imgPanel = Image(gdxGame.assetsAll.frame)
    private val lblName  = Label(tripData.nameTrip, ls54)
    private val lblDate  = Label("${tripData.dateStart} - ${tripData.dateEnd}", ls37)
    private val lblSumma = Label(tripData.summa.toSeparate(), ls48)
    private val progress = AProgress(screen)
    private val imgWhite = Image(screen.drawerUtil.getTexture(Color.WHITE))

    var isEditable = true

    override fun addActorsOnGroup() {
        addAndFillActor(imgPanel)
        addLbls()
        addProgress()

        if (isEditable.not()) {
            addActor(imgWhite)
            imgWhite.setBounds(771f, 222f, 90f, 70f)
        }

        children.onEach { it.disable() }
        setOnTouchListener(gdxGame.soundUtil) {
            TripsOpenedScreen.CURRENT_SELECTED_TRIP_INDEX = gdxGame.ds_TripData.flow.value.indexOf(tripData)
            screen.hideScreen {
                gdxGame.navigationManager.navigate(TripsOpenedScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    // Actors -------------------------------------------------------------------------------

    private fun addLbls() {
        addActors(lblName, lblDate, lblSumma)
        lblName.setBounds(54f, 234f, 782f, 48f)
        lblDate.setBounds(54f, 170f, 409f, 27f)
        lblSumma.setBounds(474f, 95f, 302f, 34f)
        lblSumma.setAlignment(Align.right)
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(40f, 34f, 809f, 28f)

        progress.progressPercentFlow.value = (10..100).random().toFloat()
    }

}