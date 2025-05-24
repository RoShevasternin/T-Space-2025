package com.goskapmepdup.dalaylamek.other

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.goskapmepdup.dalaylamek.R
import com.goskapmepdup.dalaylamek.util.CardData

class GeneralMenuFragment : Fragment() {
    private lateinit var container: LinearLayout
    private lateinit var totalPortfolioTextView: TextView
    private lateinit var totalProfitTextView: TextView

    private var totalPortfolio: Double = 0.0
    private var totalProfit: Double = 0.0
    private var isCardsLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_general_menu, container, false)
        this.container = view.findViewById(R.id.investment_container)
        totalPortfolioTextView = view.findViewById(R.id.total_portfolio_value)
        totalProfitTextView = view.findViewById(R.id.month_profit_value)

        if (!isCardsLoaded) {
            loadSavedCards()
            isCardsLoaded = true
        }
        val fadeIn = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.fade_in)
        container?.startAnimation(fadeIn)
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

    private fun addNewCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val periodInMonths = (period.toDouble() * 12).toInt().toString() 
        createCard(name, periodInMonths, percent, amount, profit)
        saveCard(name, periodInMonths, percent, amount, profit)
        saveCardToHistory(name, periodInMonths, percent, amount, profit)
        refreshCards()
    }

    private fun refreshCards() {
        container.removeAllViews() 
        val savedCards = loadCardsFromSharedPreferences() 
        
        val sortedCards = savedCards.sortedByDescending { it.profit.toDouble() }
        
        for (card in sortedCards) {
            createCard(card.name, card.period, card.percent, card.amount, card.profit)
        }
        updatePortfolioAndProfit()
    }

    private fun loadCardsFromSharedPreferences(): List<CardData> {
        val sharedPreferences = requireContext().getSharedPreferences("cards", Context.MODE_PRIVATE)
        val cardCount = sharedPreferences.getInt("cardCount", 0)
        val cards = mutableListOf<CardData>()

        for (i in 0 until cardCount) {
            val cardData = sharedPreferences.getString("card_$i", null)
            if (cardData != null) {
                val fields = cardData.split("|")
                if (fields.size == 5) {
                    val name = fields[0]
                    val period = fields[1]
                    val percent = fields[2]
                    val amount = fields[3]
                    val profit = fields[4]
                    cards.add(CardData(name, period, percent, amount, profit))
                }
            }
        }
        return cards
    }

    private fun createCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val cardView = LayoutInflater.from(context).inflate(R.layout.card_investment, container, false)

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
            container.removeView(cardView)
            deleteCard(name, period, percent, amount, profit)
            updatePortfolioAndProfit()
        }

        editButton.setOnClickListener {
            container.removeView(cardView)
            deleteCard(name, period, percent, amount, profit)

            val plusFragment = AddInvestFragment()

            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("period", period)
            bundle.putString("percent", percent)
            bundle.putString("amount", amount)
            bundle.putString("profit", profit)
            plusFragment.arguments = bundle

            cardView.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom))


            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, plusFragment)
                .addToBackStack(null)
                .commit()
        }

        container.addView(cardView)
        totalPortfolio += amount.toDouble()
        totalProfit += profit.toDouble()
        updatePortfolioAndProfit()
    }

    private fun updatePortfolioAndProfit() {
        totalPortfolioTextView.text = "$totalPortfolio ₽"
        totalProfitTextView.text = "$totalProfit ₽"

        val fadeIn = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.fade_in)
        totalPortfolioTextView.startAnimation(fadeIn)
        totalProfitTextView.startAnimation(fadeIn)
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
                    createCard(name, period, percent, amount, profit)
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
