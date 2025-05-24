package com.smarters.moneyes.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.TypingLabel
import com.smarters.moneyes.game.actors.button.AButton
import com.smarters.moneyes.game.actors.checkbox.ACheckBox
import com.smarters.moneyes.game.screens.MenuScreen
import com.smarters.moneyes.game.screens.ResultScreen
import com.smarters.moneyes.game.utils.Block
import com.smarters.moneyes.game.utils.TIME_ANIM_SCREEN
import com.smarters.moneyes.game.utils.actor.animHideSuspend
import com.smarters.moneyes.game.utils.actor.animShowSuspend
import com.smarters.moneyes.game.utils.actor.disable
import com.smarters.moneyes.game.utils.actor.setBounds
import com.smarters.moneyes.game.utils.actor.setOnClickListener
import com.smarters.moneyes.game.utils.advanced.AdvancedGroup
import com.smarters.moneyes.game.utils.advanced.AdvancedScreen
import com.smarters.moneyes.game.utils.font.FontParameter
import com.smarters.moneyes.game.utils.gdxGame
import com.smarters.moneyes.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainQuestion(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val title = listOf(
        "Логика и сравнение",
        "Финансовая грамотность",
        "Личные предпочтения",
        "Психология денег",
        "Доходы и расходы",
        "Финансовое планирование",
        "Финансовые инструменты",
        "Цели",
        "Медвежий рынок",
        "Фундаменталь-ный анализ",
        "Токенизация активов",
        "Портфель инвестиций",
    )[MenuScreen.SELECTED_LEVEL_INDEX]
    private val question = listOf(
        "Что принесёт больше прибыли через год?",
        "Какой из этих инструментов \n" +
                "чаще всего используется \n" +
                "для долгосрочного накопления?",
        "На что вы бы хотели потратить первый дополнительный доход?",
        "Какая из этих привычек поможет быстрее достичь финансовой свободы?",
        "Вы заработали 10 000 рублей \n" +
                "за неделю. Что лучше сделать \n" +
                "в первую очередь?",
        "Какую часть дохода рекомендуется откладывать ежемесячно?",
        "Что из этого может помочь увеличить доход?",
        "Какой из этих шагов приблизит \n" +
                "вас к вашей мечте?",
        "Что такое «медвежий рынок»?",
        "Что такое фундаментальный анализ?",
        "Что такое токенизация активов?",
        "Что такое портфель инвестиций?",
    )[MenuScreen.SELECTED_LEVEL_INDEX]
    private val listAnswer = listOf(
        listOf(
            "Вклад в банке под 5% годовых",
            "Ежедневное накопление с 1% дохода",
            "Покупка новой вещи",
            "Продажа автомобиля",
        ),listOf(
            "Кредитная карта",
            "Акции",
            "Банковский депозит",
            "Кредит",
        ),listOf(
            "Путешествия",
            "Покупка гаджета",
            "Инвестиции в образование",
            "Закрытие кредита",
        ),listOf(
            "Ведение бюджета",
            "Импульсивные покупки",
            "Постоянное использование кредитов",
            "Не давать в займы",
        ),listOf(
            "Потратить всё сразу",
            "Вложить в новую идею",
            "Потратить на подарки",
            "Отложить часть денег",
        ),listOf(
            "5–10%",
            "20–30%",
            "50%",
            "Всё, что останется после расходов",
        ),listOf(
            "Постоянное хранение денег в наличных",
            "Взятие кредита",
            "Вложение в инструменты с высоким процентом",
            "Покупка вещей, которые теряют стоимость",
        ),listOf(
            "Создание финансового плана",
            "Постоянное заимствование денег",
            "Покупка спиртного",
            "Игнорирование финансовых обязательств",
        ),listOf(
            "Рынок с высоким уровнем инфляции",
            "Покупка спиртного",
            "Стабильный рынок  без изменений в ценах",
            "Период, когда цены  на активы падают",
        ),listOf(
            "Оценка экономических  \n" +
                    "и финансовых факторов компании",
            "Анализ ценовых графиков",
            "Прогнозирование на основе исторических данных",
            "Оценка риска  с использованием статистики",
        ),listOf(
            "Преобразование реальных активов в цифровые токены",
            "Преобразование активов  в ценные бумаги",
            "Преобразование криптовалют в фиатные деньги",
            "Преобразование активов  в акции",
        ),listOf(
            "Список расходов",
            "Кредитная карта",
            "Набор активов",
            "Инфляция",
        ),
    )[MenuScreen.SELECTED_LEVEL_INDEX]

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font61        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(61))
    private val font49        = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(49))
    private val fontEB_61     = screen.fontGenerator_ExtraBold.generateFont(fontParameter.setSize(61))
    private val fontLT_61     = screen.fontGenerator_Light.generateFont(fontParameter.setSize(61))
    private val font_37       = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(37))

    private val ls61 = Label.LabelStyle(font61, Color.WHITE)
    private val ls49 = Label.LabelStyle(font49, Color.WHITE)
    private val ls37 = Label.LabelStyle(font_37, Color.WHITE)

    private val nameFontLight = "Light"
    private val nameFontBold  = "ExtraBold"

    private val fontLightExtraBold = Font().setFamily(Font.FontFamily(arrayOf(
        Font(fontLT_61).setName(nameFontLight),
        Font(fontEB_61).setName(nameFontBold),
    )))

    private val level    = if (MenuScreen.SELECTED_LEVEL_INDEX.inc() < 10) "0${MenuScreen.SELECTED_LEVEL_INDEX.inc()}" else MenuScreen.SELECTED_LEVEL_INDEX.inc().toString()
    private val textUsed = "[@$nameFontBold]Вопрос: $level/[@$nameFontLight][#696B74]12"

    private val lblTitle    = Label(title, ls61)
    private val lblVopros   = TypingLabel(textUsed, fontLightExtraBold)
    private val lblQuestion = Label(question, ls49)
    private val btnNext     = AButton(screen, AButton.Type.Next)
    private val listAns     = List(4) { ACheckBox(screen, ACheckBox.Type.ANSWER) }
    private val listLblAns  = List(4) { Label(listAnswer[it], ls37) }

    // Field
    private var quesY = 0f

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addQuestion()
                addVopros()
                addLbl()
                addBtnNext()
                addAnswers()
            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addLbl() {
        addActor(lblTitle)
        lblTitle.setBounds(266f, 2253f, 554f, 71f)
        lblTitle.setAlignment(Align.center)
    }

    private fun addVopros() {
        addActor(lblVopros)
        lblVopros.setBounds(61f, 1811f, 406f, 74f)
    }

    private fun addQuestion() {
        addActor(lblQuestion)
        lblQuestion.setSize(1028f, 118f)
        lblQuestion.wrap = true
        lblQuestion.height = lblQuestion.prefHeight

        quesY = 1811f - 60f - lblQuestion.height

        lblQuestion.setPosition(61f, quesY)
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(69f, 223f, 1013f, 154f)
        btnNext.disable()

        btnNext.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultScreen::class.java.name)
            }
        }
    }

    private fun addAnswers() {
        var ny = quesY - 153f - 170f
        listAns.onEach { box ->
            addActor(box)
            box.setBounds(61f, ny, 1028f, 170f)
            ny -= 60 + 170

            box.setOnCheckListener {
                if (listAns.any { it.checkFlow.value }) {
                    if (btnNext.touchable == Touchable.disabled) {
                        btnNext.enable()
                    }
                } else {
                    if (btnNext.touchable == Touchable.enabled) {
                        btnNext.disable()
                    }
                }
            }
        }

        var lblY = quesY - 153f - 170f + 18f
        listLblAns.onEach { lbl ->
            lbl.disable()
            addActor(lbl)
            lbl.setBounds(92f, lblY, 839f, 133f)
            lblY -= 133 + 97
        }
    }

    // Anim Main ------------------------------------------------

    private suspend fun animShowMain() {
        animShowSuspend(TIME_ANIM_SCREEN)
    }

    suspend fun animHideMain(block: Block = Block {}) {
        withContext(Dispatchers.Default) {
            animHideSuspend(TIME_ANIM_SCREEN)
        }
        block.invoke()
    }

}