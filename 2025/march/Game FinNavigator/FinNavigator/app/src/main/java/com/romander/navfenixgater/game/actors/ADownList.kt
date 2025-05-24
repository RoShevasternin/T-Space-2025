package com.romander.navfenixgater.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.romander.navfenixgater.game.utils.GameColor
import com.romander.navfenixgater.game.utils.TextureEmpty
import com.romander.navfenixgater.game.utils.actor.animDelay
import com.romander.navfenixgater.game.utils.actor.animHide
import com.romander.navfenixgater.game.utils.actor.animShow
import com.romander.navfenixgater.game.utils.actor.disable
import com.romander.navfenixgater.game.utils.actor.enable
import com.romander.navfenixgater.game.utils.actor.setBounds
import com.romander.navfenixgater.game.utils.actor.setOnClickListener
import com.romander.navfenixgater.game.utils.advanced.AdvancedGroup
import com.romander.navfenixgater.game.utils.advanced.AdvancedScreen
import com.romander.navfenixgater.game.utils.font.FontParameter
import com.romander.navfenixgater.game.utils.gdxGame
import com.romander.navfenixgater.util.log

class ADownList(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private var currentType = Type.Lizing

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font30        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(30))

    private val ls30 = Label.LabelStyle(font30, GameColor.black_39)

    private val imgYellowTitle = Image(gdxGame.assetsAll.yellow_title)
    private val lblSelected    = Label(currentType.nName, ls30)
    private val imgStatus      = Image(gdxGame.assetsAll.open)

    private val tmpGroup    = ATmpGroup(screen)
    private val imgDownList = Image(gdxGame.assetsAll.DOWN_LIST)
    private val imgList     = Image(gdxGame.assetsAll.list)

    private val listImgBtn = List(5) { Image() }

    private var isOpen = false

    var blockSelectType: (Type) -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgYellowTitle)
        addLblSelected()

        addActor(tmpGroup)
        tmpGroup.setBounds(-12f, -333f, 633f, 416f)
        tmpGroup.addImgDownList()

        addImgStatus()

        imgYellowTitle.setOnClickListener(gdxGame.soundUtil) {
            showDownList()
        }
    }

    private fun addImgStatus() {
        addActor(imgStatus)
        imgStatus.setBounds(560f, 29f, 24f, 21f)
    }

    private fun addLblSelected() {
        addActor(lblSelected)
        lblSelected.setBounds(27f, 31f, 95f, 20f)
    }

    // Actors Tmp Group --- DownList
    private fun AdvancedGroup.addImgDownList() {
        this.color.a = 0f
        this.disable()

        addAndFillActor(imgDownList)

        addListImgBtn()

        addActor(imgList)
        imgList.setBounds(27f, 23f, 164f, 361f)
        imgList.disable()
    }

    private fun AdvancedGroup.addListImgBtn() {
        var ny = 333f
        listImgBtn.forEachIndexed { index, img ->
            addActor(img)
            img.setBounds(0f, ny, 633f, 83f)
            ny -= 83f

            img.setOnClickListener(gdxGame.soundUtil) {
                listImgBtn.takeIf { it != img }?.forEach { it.drawable = TextureRegionDrawable(TextureEmpty) }

                img.drawable = TextureRegionDrawable(when(index) {
                    0    -> gdxGame.assetsAll.Y_TOP
                    4    -> gdxGame.assetsAll.Y_BOT
                    else -> gdxGame.assetsAll.Y_CENTER
                })

                currentType = Type.entries[index]
                blockSelectType(currentType)
                lblSelected.setText(currentType.nName)

                this@ADownList.animDelay(0.3f) { hideDownList() }
            }
        }

        listImgBtn[currentType.ordinal].drawable = TextureRegionDrawable(when(currentType.ordinal) {
            0    -> gdxGame.assetsAll.Y_TOP
            4    -> gdxGame.assetsAll.Y_BOT
            else -> gdxGame.assetsAll.Y_CENTER
        })
    }

    // Logic ------------------------------------------------------

    private fun showDownList() {
        isOpen = true
        imgStatus.drawable = TextureRegionDrawable(gdxGame.assetsAll.close)

        tmpGroup.animShow(0.25f) { tmpGroup.enable() }
    }

    private fun hideDownList() {
        isOpen = false
        imgStatus.drawable = TextureRegionDrawable(gdxGame.assetsAll.open)

        tmpGroup.disable()
        tmpGroup.animHide(0.25f)
    }

    enum class Type(val nName: String) {
        Lizing("Лизинг"),
        Credit("Кредит"),
        Ipoteca("Ипотека"),
        Deposit("Депозит"),
        Invest("Инвестиции"),
    }

}