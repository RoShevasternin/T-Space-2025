package com.axmeron.investnaveratep.game.actors.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.axmeron.investnaveratep.game.actors.ATest
import com.axmeron.investnaveratep.game.actors.ATimer
import com.axmeron.investnaveratep.game.actors.button.AButton
import com.axmeron.investnaveratep.game.screens.MenuScreen
import com.axmeron.investnaveratep.game.screens.ResultScreen
import com.axmeron.investnaveratep.game.utils.Block
import com.axmeron.investnaveratep.game.utils.TIME_ANIM_SCREEN
import com.axmeron.investnaveratep.game.utils.actor.animHide
import com.axmeron.investnaveratep.game.utils.actor.animHideSuspend
import com.axmeron.investnaveratep.game.utils.actor.animShow
import com.axmeron.investnaveratep.game.utils.actor.animShowSuspend
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedGroup
import com.axmeron.investnaveratep.game.utils.advanced.AdvancedScreen
import com.axmeron.investnaveratep.game.utils.font.FontParameter
import com.axmeron.investnaveratep.game.utils.gdxGame
import com.axmeron.investnaveratep.game.utils.runGDX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AMainTest(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val currentTitles = listOf(
        listOf(
            "Что такое диверсификация?",
            "Чем отличаются акции от облигаций?",
            "Что такое сложный процент?",
            "Какой актив считается наиболее рискованным?",
            "Что такое дивиденды?",
            "Зачем нужен брокер?",
            "Какие факторы влияют\n" + "на стоимость акций?",
            "Что такое ETF?",
        ),
        listOf(
            "Что такое финансовая подушка безопасности?",
            "Сколько процентов \n" +
                    "от дохода рекомендуется откладывать?",
            "Чем отличается дебетовая карта от кредитной?",
            "Что такое кредитная история?",
            "Как определить основные расходы?",
            "Что лучше: копить \n" +
                    "или инвестировать?",
            "Что такое пассивный доход?",
            "Как работает капитализация процентов?",
        ),
        listOf(
            "Знаете ли вы, что такое риск?",
            "Есть ли у вас финансовая подушка безопасности?",
            "Сколько времени вы готовы уделять инвестициям?",
            "Понимаете ли вы, как работают акции?",
            "Какие инструменты \n" +
                    "для вас комфортны?",
            "Какие цели вы хотите достичь?",
            "Какой срок инвестирования вам подходит?",
            "Сколько вы готовы вложить?",
        ),
        listOf(
            "Ведете ли вы учет своих доходов и расходов?",
            "Как вы определяете свои основные расходы?",
            "Есть ли у вас финансовая подушка безопасности?",
            "Как вы определяете бюджет на месяц?",
            "Используете ли вы принцип распределения доходов (например, 50/30/20)?",
            "Как часто вы пересматриваете свой бюджет?",
            "Как вы относитесь \n" +
                    "к спонтанным покупкам?",
            "Ставите ли вы финансовые цели?",
        ),
    )[MenuScreen.SELECTED_TEST_INDEX]

    private val currentAnswers = listOf(
        listOf(
            listOf(
                "Распределение инвестиций между разными активами",
                "Покупка акций одной компании",
                "Увеличение доходности",
                "Продажа активов с убытком",
            ),
            listOf(
                "Акции дают право собственности на компанию, облигации – это долг",
                "Облигации не приносят доход",
                "Акции стабильнее облигаций",
                "Акции всегда безопаснее",
            ),
            listOf(
                "Процент на сумму вклада \n" +
                        "и накопленный доход",
                "Процент, начисляемый только один раз",
                "Увеличение налога на прибыль",
                "Фиксированный процент от суммы вклада",
            ),
            listOf(
                "Криптовалюта",
                "Депозиты",
                "Акции",
                "Облигации",
            ),
            listOf(
                "Часть прибыли компании, выплачиваемая акционерам",
                "Доход от продажи акций",
                "Налог на акции",
                "Кредитная история компании",
            ),
            listOf(
                "Для совершения сделок на бирже",
                "Для хранения активов",
                "Для оценки доходности",
                "Для перевода денег",
            ),
            listOf(
                "Экономические новости",
                "Форма собственности компании",
                "Размер дивидендов",
                "Количество акционеров",
            ),
            listOf(
                "Индексный фонд, торгующийся на бирже",
                "Тип акций",
                "Долговой инструмент",
                "Фонд банковских депозитов",
            ),
        ),
        listOf(
            listOf(
                "Резерв для непредвиденных расходов",
                "Кредитная линия в банке",
                "Денежный вклад",
                "Инвестиция в акции",
            ),
            listOf(
                "10–20%",
                "30%",
                "50%",
                "5%",
            ),
            listOf(
                "Дебетовая карта позволяет тратить свои деньги, кредитная – заемные средства",
                "Кредитная карта всегда дешевле",
                "Они ничем не отличаются",
                "Дебетовая карта имеет больший лимит",
            ),
            listOf(
                "Запись обо всех кредитах \n" +
                        "и платежах заемщика",
                "Уровень дохода",
                "Налоговая декларация",
                "Срок действия кредита",
            ),
            listOf(
                "Вести учет доходов и расходов",
                "Установить лимиты",
                "Применить кредитные инструменты",
                "Использовать кредитные карты",
            ),
            listOf(
                "Инвестировать для роста капитала",
                "Занимать деньги",
                "Использовать кредиты",
                "Только копить",
            ),
            listOf(
                "Доход, получаемый \n" +
                        "без активного участия",
                "Процент по кредиту",
                "Деньги от займа",
                "Доход от работы",
            ),
            listOf(
                "Проценты добавляются \n" +
                        "к основной сумме вклада",
                "Сумма вклада уменьшается",
                "Увеличивается налоговая нагрузка",
                "Проценты начисляются один раз",
            ),
        ),
        listOf(
            listOf(
                "Вероятность убытков",
                "Доходность активов",
                "Уровень вложений",
                "Финансовая стратегия",
            ),
            listOf(
                "Да",
                "Нет",
                "Планирую создать",
                "Не знаю, что это",
            ),
            listOf(
                "1 час в день",
                "1 час в неделю",
                "По необходимости",
                "Не готов",
            ),
            listOf(
                "Да",
                "Нет",
                "Частично",
                "Не уверен",
            ),
            listOf(
                "Акции и облигации",
                "Криптовалюта",
                "Недвижимость",
                "Никакие",
            ),
            listOf(
                "Увеличение дохода",
                "Нет цели",
                "Пассивный доход",
                "Сохранение капитала",
            ),
            listOf(
                "Долгосрочный",
                "Краткосрочный",
                "Менее года",
                "Не знаю",
            ),
            listOf(
                "Небольшую сумму для старта",
                "Всю зарплату",
                "Половину накоплений",
                "Не готов",
            ),
        ),
        listOf(
            listOf(
                "Да, записываю все доходы \n" +
                        "и расходы ежедневно",
                "Иногда, только крупные траты",
                "Нет, но думаю, что это полезно",
                "Никогда этого не делаю",
            ),
            listOf(
                "Анализирую прошлые траты\n" +
                        " и выделяю категории",
                "Примерно знаю, на что уходит большая часть денег",
                "Ориентируюсь \n" +
                        "по остаткам на счете",
                "Не задумываюсь о расходах",
            ),
            listOf(
                "Да, есть накопления \n" +
                        "на 3-6 месяцев жизни",
                "Есть немного денег, \n" +
                        "но их недостаточно",
                "Только в виде кредитной линии",
                "Нет, трачу всё до последнего",
            ),
            listOf(
                "Планирую доходы \n" +
                        "и расходы заранее",
                "Ограничиваюсь только крупными покупками",
                "Живу без бюджета",
                "Смотрю на текущие потребности и определяю примерный бюджет",
            ),
            listOf(
                "Да, строго следую этому принципу",
                "Частично, только для сбережений",
                "Нет, но слышал(а) об этом",
                "Не знаю, что это такое",
            ),
            listOf(
                "Регулярно, раз в месяц или чаще",
                "Когда возникают финансовые трудности",
                "Очень редко, раз в год или по необходимости",
                "Никогда не пересматриваю",
            ),
            listOf(
                "Планирую их заранее \n" +
                        "и выделяю для этого бюджет",
                "Покупаю, если вижу что-то интересное",
                "Часто совершаю спонтанные покупки, не задумываясь",
                "Иногда позволяю себе, если остается лишнее",
            ),
            listOf(
                "Да, четко прописываю цели \n" +
                        "и сроки их достижения",
                "У меня есть идеи, \n" +
                        "но без конкретных планов",
                "Думаю об этом, но ничего \n" +
                        "не предпринимаю",
                "Никогда не ставлю целей",
            ),
        ),
    )[MenuScreen.SELECTED_TEST_INDEX]

    private var currentTestIndex = 0

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val fontSB_43     = screen.fontGenerator_Bold.generateFont(fontParameter.setSize(43))

    private val lsSB_43 = Label.LabelStyle(fontSB_43, Color.WHITE)

    private val btnBack  = AButton(screen, AButton.Type.Back)
    private val lblTitle = Label("${currentTestIndex.inc()} из 8", lsSB_43)
    private val btnNext  = AButton(screen, AButton.Type.Next)
    private var test     = ATest(screen, currentTitles[currentTestIndex], currentAnswers[currentTestIndex])
    private val timer    = ATimer(screen)

    override fun addActorsOnGroup() {
        coroutine?.launch {
            runGDX {
                color.a = 0f

                addBtnBack()
                addLblTitle()
                addBtnNext()
                addTest()
                addTimer()

            }

            animShowMain()
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(53f, 1529f, 75f, 75f)
        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }

    }

    private fun addLblTitle() {
        addActor(lblTitle)
        lblTitle.setBounds(337f, 1550f, 119f, 30f)
    }

    private fun addTimer() {
        addActor(timer)
        timer.setBounds(566f, 1527f, 180f, 75f)
        timer.blockFinish = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultScreen::class.java.name, screen::class.java.name)
            }
        }
    }

    private fun addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(190f, 214f, 425f, 97f)
        btnNext.setOnClickListener {
            currentTestIndex++

            if (currentTestIndex <= currentTitles.lastIndex) {
                lblTitle.setText("${currentTestIndex.inc()} из 8")

                test.animHide(0.25f) {
                    test.remove()
                    test = ATest(screen, currentTitles[currentTestIndex], currentAnswers[currentTestIndex])
                    test.color.a = 0f
                    addTest()
                    test.animShow(0.25f)
                }
            } else {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ResultScreen::class.java.name, screen::class.java.name)
                }
            }
        }
    }

    private fun addTest() {
        addActor(test)
        test.setBounds(53f, 622f, 695f, 775f)
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