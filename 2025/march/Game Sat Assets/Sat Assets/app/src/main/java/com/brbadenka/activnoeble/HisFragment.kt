package com.brbadenka.activnoeble

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

/**
 * Фрагмент для отображения "Архива вкладов".
 * Здесь просто берём сохранённые карточки из SharedPreferences ("history"),
 * показываем, можем удалять/редактировать (опционально).
 */
class HisFragment : Fragment() {

    private lateinit var historyContainer: LinearLayout
    private lateinit var totalPortfolioTextView: TextView
    private lateinit var totalProfitTextView: TextView
    private lateinit var cardView: CardView

    private var totalPortfolio: Double = 0.0
    private var totalProfit: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_his, container, false)

        historyContainer = view.findViewById(R.id.history_container)
        totalPortfolioTextView = view.findViewById(R.id.total_portfolio_value)
        totalProfitTextView = view.findViewById(R.id.month_profit_value)
        cardView = view.findViewById(R.id.cardView)

        // Градиент + скруглённые углы для "шапки"
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.parseColor("#24B23E"),
                Color.parseColor("#5ACB3A")
            )
        )
        gradientDrawable.cornerRadius = 20f
        cardView.background = gradientDrawable

        // Подтянем значения общего портфеля/прибыли (если нужно показывать тут)
        loadTotalValues()

        // Загружаем сам архив
        loadSavedHistoryCards()

        return view
    }

    /**
     * Загрузка totalPortfolio / totalProfit (при желании).
     */
    private fun loadTotalValues() {
        val sharedPreferences = requireContext().getSharedPreferences("totals", Context.MODE_PRIVATE)
        totalPortfolio = sharedPreferences.getFloat("totalPortfolio", 0f).toDouble()
        totalProfit = sharedPreferences.getFloat("totalProfit", 0f).toDouble()

        totalPortfolioTextView.text = "$totalPortfolio ₽"
        totalProfitTextView.text = "$totalProfit ₽"
    }

    /**
     * Создаём карточку в архиве. (Без кнопок, или же прячем их.)
     */
    fun addHistoryCard(name: String, period: String, percent: String, amount: String, profit: String, date: String) {
        val cardView = LayoutInflater.from(context).inflate(R.layout.card_investment, historyContainer, false)

        val nameTextView = cardView.findViewById<TextView>(R.id.depositTitle)
        val periodTextView = cardView.findViewById<TextView>(R.id.depositPeriod)
        val percentTextView = cardView.findViewById<TextView>(R.id.depositRate)
        val amountTextView = cardView.findViewById<TextView>(R.id.depositAmount)
        val profitTextView = cardView.findViewById<TextView>(R.id.depositProfit)
        val deleteButton = cardView.findViewById<ImageView>(R.id.deleteLabel)
        val editButton = cardView.findViewById<ImageView>(R.id.editLabel)

        // Прячем кнопки, если в архиве редактирование не нужно
        deleteButton.visibility = View.GONE
        editButton.visibility = View.GONE

        nameTextView.text = name
        periodTextView.text = "$period месяцев"
        percentTextView.text = "$percent%"
        amountTextView.text = "$amount ₽"
        profitTextView.text = "$profit ₽"

        historyContainer.addView(cardView, 0) // добавим сверху
    }

    /**
     * Грузим архивные карточки из SharedPreferences ("history").
     */
    private fun loadSavedHistoryCards() {
        val sp = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val historyCount = sp.getInt("historyCount", 0)

        for (i in 0 until historyCount) {
            val cardData = sp.getString("history_card_$i", null)
            if (cardData != null) {
                // name|period|percent|amount|profit|date
                val parts = cardData.split("|")
                if (parts.size == 6) {
                    val name = parts[0]
                    val period = parts[1]
                    val percent = parts[2]
                    val amount = parts[3]
                    val profit = parts[4]
                    val date = parts[5]

                    addHistoryCard(name, period, percent, amount, profit, date)
                }
            }
        }
    }

    /**
     * Удалить карточку из "history" (если вдруг нужно).
     * Исправлена логика: читаем "historyCount" и сравниваем 6 полей.
     */
    private fun deleteHistoryCard(name: String, period: String, percent: String, amount: String, profit: String, date: String) {
        val sharedPreferences = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val historyCount = sharedPreferences.getInt("historyCount", 0)
        val newCardList = mutableListOf<String>()

        for (i in 0 until historyCount) {
            val data = sharedPreferences.getString("history_card_$i", null)
            // Сравниваем все 6 полей
            if (data != null && data != "$name|$period|$percent|$amount|$profit|$date") {
                newCardList.add(data)
            }
        }

        editor.clear()
        editor.putInt("historyCount", newCardList.size)
        for (i in newCardList.indices) {
            editor.putString("history_card_$i", newCardList[i])
        }
        editor.apply()
    }
}
