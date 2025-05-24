package com.busiknesik.pomeshnek.game.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.busiknesik.pomeshnek.game.data.TovarData
import com.busiknesik.pomeshnek.game.screens.AddScreen
import com.busiknesik.pomeshnek.game.utils.actor.setOnClickListener
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedGroup
import com.busiknesik.pomeshnek.game.utils.advanced.AdvancedScreen
import com.busiknesik.pomeshnek.game.utils.gdxGame
import com.busiknesik.pomeshnek.game.utils.toSeparate

class ATovar(
    override val screen: AdvancedScreen,
    val tovar  : TovarData,
    val lsB_58 : Label.LabelStyle,
    val lsBG_58: Label.LabelStyle,
    val lsR_38 : Label.LabelStyle,
): AdvancedGroup() {

    private val main       = Image(gdxGame.assetsAll.tovar)
    private val lblName    = Label(tovar.dName, lsB_58)
    private val lblDate    = Label(tovar.date, lsR_38)
    private val lblSumma   = Label(tovar.summa.toSeparate() + " ₽", lsBG_58)
    private val lblCount   = Label(tovar.count.toSeparate() + " шт", lsBG_58)
    private val lblMarge   = Label(tovar.marge.toSeparate() + "%", lsBG_58)
    private val lblPribl   = Label(tovar.prible.toSeparate() + " ₽", lsBG_58)

    private val aDelete = Actor()
    private val aEdit   = Actor()

    override fun addActorsOnGroup() {
        addAndFillActor(main)
        addLbls()
        addBtns()
    }

    // Actors ----------------------------------------------------------------

    private fun addLbls() {
        addActors(lblName, lblDate, lblSumma, lblCount, lblMarge, lblPribl)
        lblName.apply {
            setBounds(93f, 743f, 161f, 41f)
            //setAlignment(Align.center)
        }
        lblDate.apply {
            setBounds(93f, 668f, 192f, 27f)
            //setAlignment(Align.center)
        }
        lblSumma.apply {
            setBounds(79f, 433f, 270f, 41f)
            //setAlignment(Align.left)
        }
        lblCount.apply {
            setBounds(691f, 433f, 277f, 41f)
            setAlignment(Align.right)
        }
        lblMarge.apply {
            setBounds(79f, 213f, 100f, 41f)
            setAlignment(Align.left)
        }
        lblPribl.apply {
            setBounds(734f, 213f, 234f, 41f)
            setAlignment(Align.right)
        }
    }

    private fun addBtns() {
        addActors(aDelete, aEdit)
        aDelete.apply {
            setBounds(42f, 26f, 343f, 114f)
            setOnClickListener(gdxGame.soundUtil) {
                gdxGame.ds_Tovar.update {
                    val mList = it.toMutableList()
                    mList.remove(tovar)
                    mList
                }
                gdxGame.navigationManager.navigate(screen::class.java.name)
            }
        }
        aEdit.apply {
            setBounds(615f, 26f, 343f, 114f)
            setOnClickListener(gdxGame.soundUtil) {
                gdxGame.ds_Tovar.update {
                    val mList = it.toMutableList()
                    mList.remove(tovar)
                    mList
                }
                gdxGame.navigationManager.navigate(AddScreen::class.java.name, screen::class.java.name)
            }
        }
    }

}