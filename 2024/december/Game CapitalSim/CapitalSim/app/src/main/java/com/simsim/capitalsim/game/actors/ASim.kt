package com.simsim.capitalsim.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.List
import com.simsim.capitalsim.game.actors.autoLayout.AVerticalGroup
import com.simsim.capitalsim.game.actors.button.AButton
import com.simsim.capitalsim.game.actors.button.AButtonOut
import com.simsim.capitalsim.game.actors.checkbox.ACheckBoxGroup
import com.simsim.capitalsim.game.actors.main.AMainMenu
import com.simsim.capitalsim.game.screens.ResultScreen
import com.simsim.capitalsim.game.utils.GameColor
import com.simsim.capitalsim.game.utils.TIME_ANIM_SCREEN
import com.simsim.capitalsim.game.utils.actor.animHide
import com.simsim.capitalsim.game.utils.actor.animShow
import com.simsim.capitalsim.game.utils.actor.setBounds
import com.simsim.capitalsim.game.utils.actor.setOnClickListener
import com.simsim.capitalsim.game.utils.advanced.AdvancedGroup
import com.simsim.capitalsim.game.utils.advanced.AdvancedScreen
import com.simsim.capitalsim.game.utils.font.FontParameter
import com.simsim.capitalsim.game.utils.gdxGame
import com.simsim.capitalsim.util.log
import kotlin.math.absoluteValue

class ASim(
    override val screen: AdvancedScreen
): AdvancedGroup() {

    private val currentIndex = AMainMenu.CATEGORY_INDEX

    var blockNext = {}

    // Основы инвестирования
    private val listQuest_1 = listOf(
        "Что такое диверсификация?",
        "Что считается низкорискованным активом?",
        "Что важно при выборе инвестиции?",
        "Что лучше для долгосрочных инвестиций?",
        "Что такое \"подушка безопасности\"?",
        "Когда стоит продавать активы?",
        "Когда стоит продавать активы?",
    )
    private val listQuest_2 = listOf(
        "Что важно учитывать при составлении бюджета?",
        "Что такое \"подушка безопасности\"?",
        "Какой процент дохода рекомендуется откладывать?",
        "Что лучше: тратить весь доход или откладывать?",
        "Какой долг считается плохим?",
        "Когда стоит продавать активы?",
        "Что нужно делать, чтобы избежать долгов?",
    )
    private val listQuest_3 = listOf(
        "Что является основой успешной бизнес-инвестиции?",
        "Куда лучше вложить деньги для устойчивого роста?",
        "Что отличает успешного бизнес-инвестора?",
        "Что нужно учитывать при инвестировании в новый стартап?",
        "Во что выгоднее вложить деньги: кофейню или ферму?",
        "Что лучше не делать при вложении в бизнес?",
        "Что нужно делать, чтобы избежать долгов?",
    )
    private val listQuest_4 = listOf(
        "Что нужно учитывать при выборе объекта недвижимости для инвестиций?",
        "Какой тип недвижимости чаще всего выбирают для стабильного дохода?",
        "Что важно знать о состоянии рынка перед покупкой недвижимости?",
        "Что считается хорошим признаком для инвестиций в недвижимость?",
        "Что выгоднее: купить квартиру или коммерческое помещение?",
        "Когда лучше продавать недвижимость?",
        "Что помогает снизить риски при вложении в недвижимость?",
    )

    private val listQuest = listOf(
        listQuest_1,
        listQuest_2,
        listQuest_3,
        listQuest_4,
    )[currentIndex]

    private val listText_1 = listOf(
        listOf(
            "Распределение инвестиций для снижения рисков",
            "Инвестирование только в один актив",
            "Увеличение доходности акций",
        ),
        listOf(
            "Криптовалюта",
            "Акции стартапов",
            "Депозиты в банке",
        ),
        listOf(
            "Потенциал роста и риски",
            "Рекомендации друзей",
            "Популярность актива",
        ),
        listOf(
            "Высокорискованные стартапы",
            "Индексные фонды",
            "Ставки на спортивные события",
        ),
        listOf(
            "Резерв на непредвиденные расходы",
            "Личный кабинет в банке",
            "Резерв для покупки акций",
        ),
        listOf(
            "Как только они выросли на 5%",
            "При первом падении стоимости",
            "Когда их стоимость достигла цели",
        ),
        listOf(
            "Как только они выросли на 5%",
            "Когда их стоимость достигла цели",
            "При первом падении стоимости",
        ),
    )
    private val listText_2 = listOf(
        listOf(
            "Доходы и расходы",
            "Только крупные траты",
            "Ежемесячные подарки",
        ),
        listOf(
            "Дополнительный кредит в банке",
            "Доход от инвестиций",
            "Резерв на случай непредвиденных расходов",
        ),
        listOf(
            "10-20%",
            "50%",
            "Всё, что останется",
        ),
        listOf(
            "Всё зависит от текущих расходов",
            "Всегда откладывать часть дохода",
            "Не имеет значения",
        ),
        listOf(
            "Кредит на отпуск",
            "Ипотека",
            "Инвестиционный кредит",
        ),
        listOf(
            "Размер вашего кредита",
            "Максимальная сумма займа",
            "Оценка вашей кредитной истории",
        ),
        listOf(
            "Брать больше кредитов",
            "Планировать бюджет и избегать импульсивных покупок",
            "Тратить меньше, чем зарабатываешь",
        ),
    )
    private val listText_3 = listOf(
        listOf(
            "Анализ рынка и финансовых показателей",
            "Случайный выбор перспективной идеи",
            "Большие вложения в рекламу",
        ),
        listOf(
            "В хобби друга",
            "В любую модную идею",
            "В бизнес с высоким спросом",
        ),
        listOf(
            "Способность оценивать риски и принимать взвешенные решения",
            "Интуиция и удача",
            "Готовность рисковать всем капиталом",
        ),
        listOf(
            "Команду, рынок и бизнес-модель",
            "Личное мнение основателя стартапа",
            "Красивый сайт компании",
        ),
        listOf(
            "Ферму, если в регионе высокий спрос на сельхозпродукты",
            "Кофейню в спальном районе",
            "Это не имеет значения",
        ),
        listOf(
            "Диверсифицировать вложения",
            "Анализировать бизнес-план",
            "Инвестировать без понимания рынка",
        ),
        listOf(
            "Брать больше кредитов",
            "Планировать бюджет и избегать импульсивных покупок",
            "Тратить меньше, чем зарабатываешь",
        ),
    )
    private val listText_4 = listOf(
        listOf(
            "Расположение и инфраструктуру",
            "Цвет стен в помещении",
            "Декоративные элементы интерьера",
        ),
        listOf(
            "Элитные особняки",
            "Земли под сельское хозяйство",
            "Жилые квартиры для аренды",
        ),
        listOf(
            "Текущие цены и тенденции",
            "Отзывы соседей",
            "Историю предыдущих владельцев",
        ),
        listOf(
            "Большая площадь объекта",
            "Активное развитие района",
            "Современный дизайн здания",
        ),
        listOf(
            "Коммерческое помещение, если есть постоянный арендатор",
            "Квартиру в спальном районе",
            "Загородный дом для отдыха",
        ),
        listOf(
            "Когда объект требует ремонта",
            "Как только появится первый покупатель",
            "На пике роста цен",
        ),
        listOf(
            "Покупка самой дешёвой недвижимости",
            "Диверсификация вложений в разные типы объектов",
            "Игнорирование анализа рынка",
        ),
    )

    private val listAnswer = listOf(
        listText_1,
        listText_2,
        listText_3,
        listText_4,
    )[currentIndex]

    private var currentAskIndex = 0

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font_B_53     = screen.fontGenerator_InterBold.generateFont(fontParameter.setSize(53))
    private val font_R_43     = screen.fontGenerator_InterBold.generateFont(fontParameter.setSize(43))

    private val ls53 = LabelStyle(font_B_53, GameColor.black_0B)
    private val ls43 = LabelStyle(font_R_43, Color.BLACK)

    private val imgPanel      = Image(gdxGame.assetsAll.panel)
    private val verticalGroup = AVerticalGroup(screen, paddingTop = 42f, paddingBottom = 42f, isWrap = true)
    private val imgIcon       = Image(gdxGame.assetsAll.listI[currentIndex])
    private val lblQ          = Label(listQuest[currentAskIndex], ls53)
    private val cbg           = ACheckBoxGroup()
    private val listA         = List(3) { AAnswer(screen, ls43, cbg) }
    private val btnNext       = AButtonOut(screen, AButtonOut.Type.Next)

    // Field


    override fun addActorsOnGroup() {
        val startY = y

        addAndFillActor(imgPanel)
        addActor(verticalGroup)
        verticalGroup.setBounds(42f, 0f, 813f, height)

        verticalGroup.blockHeight = { verticalHeight ->
            when {
                verticalHeight > 1534f -> verticalHeight
                verticalHeight < 1534f -> 1534f
                else -> 1534f
            }.also { newHeight: Float ->
                val result = (newHeight - 1534f).absoluteValue
                height = newHeight
                y      = startY - result
                imgPanel.height = height
            }
        }

        verticalGroup.apply {
            addImgIcon()
            addSpace(70f)
            addLblQ()
            addSpace(42f)
            addListA()
            addSpace(150f)
            addBtnNext()

            //children.onEach { it.debug() }
        }
    }

    // Actors -------------------------------------------------------------------------------------------------

    private fun AVerticalGroup.addSpace(space: Float) {
        addActor(Actor().apply { height = space })
    }

    private fun AVerticalGroup.addImgIcon() {
        imgIcon.setSize(813f, 376f)
        addActor(imgIcon)
    }

    private fun AVerticalGroup.addLblQ() {
        lblQ.setSize(813f, 70f)
        lblQ.setWrap(true)
        addActor(lblQ)

        lblQ.apply {  height = prefHeight }
    }

    private fun AVerticalGroup.addListA() {
        listA.onEachIndexed { index, aAnswer ->
            aAnswer.setSize(813f, 140f)
            addActor(aAnswer)
            if (index == 0) aAnswer.box.check(false)
            aAnswer.setText(listAnswer[currentAskIndex][index])

            addSpace(32f)
        }
    }

    private fun AVerticalGroup.addBtnNext() {
        btnNext.setSize(813f, 193f)
        addActor(btnNext)
        btnNext.setOnClickListener {
            if (currentAskIndex + 1 == 6) {
               screen.hideScreen {
                   gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
               }
            } else {
                currentAskIndex++
                update()
            }
        }
    }

    // Logic -------------------------------------------------------------------------------------------------

    private fun update() {
        blockNext()

        animHide(TIME_ANIM_SCREEN) {
            lblQ.apply {
                setText(listQuest_1[currentAskIndex])
                height = prefHeight
            }
            listA.onEachIndexed { index, aAnswer ->
                aAnswer.setText(listAnswer[currentAskIndex][index])
            }

            animShow(TIME_ANIM_SCREEN)
        }
    }

}