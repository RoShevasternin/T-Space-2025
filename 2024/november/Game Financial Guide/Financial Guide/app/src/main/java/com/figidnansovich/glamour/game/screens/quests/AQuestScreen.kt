package com.figidnansovich.glamour.game.screens.quests

import com.figidnansovich.glamour.R
import com.figidnansovich.glamour.appContext
import com.figidnansovich.glamour.game.LibGDXGame

class AQuestScreen(override val game: LibGDXGame) : AbstractQuestScreen() {

    override val listTitle : List<String> = appContext.resources.getStringArray(R.array.quest_title_1).toList()
    override val listAnswer: List<List<String>> = listOf(
        listOf(
            "Планирование расходов и доходов на определённый период времени",
            "Способ подсчета накоплений на банковском счету",
            "Ежемесячный отчёт от налоговой службы",
            "Список покупок на неделю",
        ),
        listOf(
            "Фонд для покрытия непредвиденных расходов",
            "Накопления на поездку в отпуск",
            "Вклад в инвестиционные активы",
            "Денежные средства, отложенные для крупных покупок",
        ),
        listOf(
            "20%",
            "10%",
            "5%",
            "50%",
        ),
        listOf(
            "Кредит на покупку бытовой техники под высокий процент",
            "Ипотечный кредит на покупку недвижимости",
            "Инвестиционный кредит на развитие бизнеса.",
            "Автокредит с низкой процентной ставкой",
        ),
        listOf(
            "Распределение финансовых активов по разным категориям для снижения риска",
            "Увеличение расходов для стимулирования экономического роста",
            "Продажа активов для увеличения ликвидности",
            "Снижение налогового бремени за счёт использования различных юридических схем",
        ),
        listOf(
            "Оплата коммунальных услуг",
            "Покупка нового смартфона",
            "Покупка билетов на концерт",
            "Покупка одежды",
        ),

    )

}