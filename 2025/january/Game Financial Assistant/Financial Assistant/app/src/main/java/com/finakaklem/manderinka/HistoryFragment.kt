package com.finakaklem.manderinka

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {

    private lateinit var historyContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyContainer = view.findViewById(R.id.history_container)

        android.util.Log.d("HistoryFragment", "HistoryFragment created and loading cards...")

        loadSavedHistoryCards()

        return view
    }

    fun addHistoryCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val cardView = LayoutInflater.from(context).inflate(R.layout.card_investment, historyContainer, false)

        val nameTextView = cardView.findViewById<TextView>(R.id.depositTitle)
        val periodTextView = cardView.findViewById<TextView>(R.id.depositPeriod)
        val percentTextView = cardView.findViewById<TextView>(R.id.depositRate)
        val amountTextView = cardView.findViewById<TextView>(R.id.depositAmount)
        val profitTextView = cardView.findViewById<TextView>(R.id.depositProfit)
        val deleteButton = cardView.findViewById<ImageView>(R.id.deleteLabel)
        val editButton = cardView.findViewById<ImageView>(R.id.editLabel)

        nameTextView.text = name
        periodTextView.text = "$period месяцев"
        percentTextView.text = "$percent%"
        amountTextView.text = "$amount ₽"
        profitTextView.text = "$profit ₽"

        // Обработчик для удаления карточки
        deleteButton.setOnClickListener {
            historyContainer.removeView(cardView)
            deleteHistoryCard(name, period, percent, amount, profit)
        }

        // Обработчик для редактирования карточки
        editButton.setOnClickListener {
            historyContainer.removeView(cardView)
            deleteHistoryCard(name, period, percent, amount, profit)

            // Открываем PlusFragment для редактирования
            val plusFragment = PlusFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("period", period)
            bundle.putString("percent", percent)
            bundle.putString("amount", amount)
            bundle.putString("profit", profit)
            plusFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, plusFragment)
                .addToBackStack(null)
                .commit()
        }

        historyContainer.addView(cardView)
    }

    // Загрузка сохраненных карточек из SharedPreferences
    private fun loadSavedHistoryCards() {
        val sharedPreferences = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val cardCount = sharedPreferences.getInt("cardCount", 0)

        for (i in 0 until cardCount) {
            val cardData = sharedPreferences.getString("history_card_$i", null)
            if (cardData != null) {
                val cardFields = cardData.split("|")
                if (cardFields.size == 5) {
                    val name = cardFields[0]
                    val period = cardFields[1]
                    val percent = cardFields[2]
                    val amount = cardFields[3]
                    val profit = cardFields[4]

                    android.util.Log.d("HistoryFragment", "Loaded card: $name, $period, $percent, $amount, $profit")

                    addHistoryCard(name, period, percent, amount, profit)
                }
            }
        }
    }

    // Удаление карточки из SharedPreferences
    private fun deleteHistoryCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val sharedPreferences = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val cardCount = sharedPreferences.getInt("cardCount", 0)
        val newCardList = mutableListOf<String>()

        for (i in 0 until cardCount) {
            val cardData = sharedPreferences.getString("history_card_$i", null)
            if (cardData != null && cardData != "$name|$period|$percent|$amount|$profit") {
                newCardList.add(cardData)
            }
        }

        editor.clear()
        editor.putInt("cardCount", newCardList.size)

        for (i in newCardList.indices) {
            editor.putString("history_card_$i", newCardList[i])
        }

        editor.apply()
    }
}
