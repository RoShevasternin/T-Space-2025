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

class HomeFragment : Fragment() {

    private lateinit var investmentContainer: LinearLayout
    private lateinit var totalPortfolioTextView: TextView
    private lateinit var totalProfitTextView: TextView

    private var totalPortfolio: Double = 0.0
    private var totalProfit: Double = 0.0
    private var isCardsLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        investmentContainer = view.findViewById(R.id.investment_container)
        totalPortfolioTextView = view.findViewById(R.id.total_portfolio_value)
        totalProfitTextView = view.findViewById(R.id.month_profit_value)

        if (!isCardsLoaded) {
            loadSavedCards()
            isCardsLoaded = true
        }

        val name = arguments?.getString("name")
        val period = arguments?.getString("period")
        val percent = arguments?.getString("percent")
        val amount = arguments?.getString("amount")
        val profit = arguments?.getString("profit")

        if (name != null && period != null && percent != null && amount != null && profit != null) {
            addNewCard(name, period, percent, amount, profit)
            clearBundle()
        }

        return view
    }

    fun addNewCard(name: String, period: String, percent: String, amount: String, profit: String) {
        createCard(name, period, percent, amount, profit)
        saveCard(name, period, percent, amount, profit)
        saveCardToHistory(name, period, percent, amount, profit)
    }

    private fun createCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val cardView = LayoutInflater.from(context).inflate(R.layout.card_investment, investmentContainer, false)

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

        deleteButton.setOnClickListener {
            investmentContainer.removeView(cardView)
            deleteCard(name, period, percent, amount, profit)
            updatePortfolioAndProfit()
        }

        editButton.setOnClickListener {
            investmentContainer.removeView(cardView)
            deleteCard(name, period, percent, amount, profit)

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

        investmentContainer.addView(cardView)
        totalPortfolio += amount.toDouble()
        totalProfit += profit.toDouble()
        updatePortfolioAndProfit()
    }

    private fun updatePortfolioAndProfit() {
        totalPortfolioTextView.text = "$totalPortfolio ₽"
        totalProfitTextView.text = "$totalProfit ₽"
    }

    private fun deleteCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val sharedPreferences = requireContext().getSharedPreferences("cards", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val cardCount = sharedPreferences.getInt("cardCount", 0)
        val newCardList = mutableListOf<String>()

        for (i in 0 until cardCount) {
            val cardData = sharedPreferences.getString("card_$i", null)
            if (cardData != null && cardData != "$name|$period|$percent|$amount|$profit") {
                newCardList.add(cardData)
            }
        }

        editor.clear()
        editor.putInt("cardCount", newCardList.size)

        for (i in newCardList.indices) {
            editor.putString("card_$i", newCardList[i])
        }

        editor.apply()

        totalPortfolio -= amount.toDouble()
        totalProfit -= profit.toDouble()
        updatePortfolioAndProfit()
    }

    private fun saveCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val sharedPreferences = requireContext().getSharedPreferences("cards", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val cardCount = sharedPreferences.getInt("cardCount", 0)
        editor.putString("card_$cardCount", "$name|$period|$percent|$amount|$profit")
        editor.putInt("cardCount", cardCount + 1)
        editor.apply()
    }

    private fun loadSavedCards() {
        val sharedPreferences = requireContext().getSharedPreferences("cards", Context.MODE_PRIVATE)
        val cardCount = sharedPreferences.getInt("cardCount", 0)

        for (i in 0 until cardCount) {
            val cardData = sharedPreferences.getString("card_$i", null)
            if (cardData != null) {
                val cardFields = cardData.split("|")
                if (cardFields.size == 5) {
                    val name = cardFields[0]
                    val period = cardFields[1]
                    val percent = cardFields[2]
                    val amount = cardFields[3]
                    val profit = cardFields[4]
                    totalPortfolio += amount.toDouble()
                    totalProfit += profit.toDouble()
                    createCard(name, period, percent, amount, profit) // Используем только создание карточки
                }
            }
        }

        updatePortfolioAndProfit()
    }
    private fun saveCardToHistory(name: String, period: String, percent: String, amount: String, profit: String) {
        val sharedPreferences = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val cardCount = sharedPreferences.getInt("cardCount", 0)
        editor.putString("history_card_$cardCount", "$name|$period|$percent|$amount|$profit")
        editor.putInt("cardCount", cardCount + 1)
        editor.apply()
    }
    private fun clearBundle() {
        arguments?.clear()
    }
}
