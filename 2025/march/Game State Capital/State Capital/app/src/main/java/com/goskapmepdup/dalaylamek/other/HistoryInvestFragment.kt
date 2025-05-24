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
class HistoryInvestFragment : Fragment() {
    private lateinit var container: LinearLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_invest, container, false)
        this.container = view.findViewById(R.id.history_container)

        android.util.Log.d("HistoryFragment", "HistoryFragment created and loading cards...")

        loadCards()

        return view
    }

    fun addCard(name: String, period: String, percent: String, amount: String, profit: String) {
        val cardView = LayoutInflater.from(context).inflate(R.layout.card_investment, container, false)

        val nameInvest = cardView.findViewById<TextView>(R.id.depositTitle)
        val periodInvest = cardView.findViewById<TextView>(R.id.depositPeriod)
        val percentInvest = cardView.findViewById<TextView>(R.id.depositRate)
        val amoundInvest = cardView.findViewById<TextView>(R.id.depositAmount)
        val profitInvest = cardView.findViewById<TextView>(R.id.depositProfit)
        val deleteButton = cardView.findViewById<ImageView>(R.id.deleteLabel)
        val editButton = cardView.findViewById<ImageView>(R.id.editLabel)

        nameInvest.text = name
        periodInvest.text = "$period месяцев"
        percentInvest.text = "$percent%"
        amoundInvest.text = "$amount ₽"
        profitInvest.text = "$profit ₽"

        deleteButton.setOnClickListener {
            container.removeView(cardView)
            deleteCards(name, period, percent, amount, profit)
        }

        editButton.setOnClickListener {
            container.removeView(cardView)
            deleteCards(name, period, percent, amount, profit)

            val plusFragment = AddInvestFragment()
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
        cardView.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom))
        container.addView(cardView)
    }

    private fun loadCards() {
        val sharedPreferences = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val cardCount = sharedPreferences.getInt("cardCount", 0)
        container.removeAllViews()
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

                    container.postDelayed({
                        addCard(name, period, percent, amount, profit)
                    }, i * 200L)
                }
            }
        }
    }

    private fun deleteCards(name: String, period: String, percent: String, amount: String, profit: String) {
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
