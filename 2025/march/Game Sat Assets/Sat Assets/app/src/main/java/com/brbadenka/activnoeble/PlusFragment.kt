package com.brbadenka.activnoeble

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView

class PlusFragment : Fragment() {
    private lateinit var nameEditText: EditText
    private lateinit var periodEditText: EditText
    private lateinit var percentEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var acceptButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var totalPortfolioTextView: TextView
    private lateinit var totalProfitTextView: TextView
    private var totalPortfolio: Double = 0.0
    private var totalProfit: Double = 0.0
    private lateinit var cardView: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plus, container, false)
        nameEditText = view.findViewById(R.id.nameEditText)
        periodEditText = view.findViewById(R.id.periodEditText)
        percentEditText = view.findViewById(R.id.percentEditText)
        amountEditText = view.findViewById(R.id.amountEditText)
        acceptButton = view.findViewById(R.id.acceptButton)
        deleteButton = view.findViewById(R.id.deleteButton)
        totalPortfolioTextView = view.findViewById(R.id.total_portfolio_value)
        totalProfitTextView = view.findViewById(R.id.month_profit_value)
        cardView = view.findViewById(R.id.cardView)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.parseColor("#24B23E"),
                Color.parseColor("#5ACB3A")
            )
        )
        gradientDrawable.cornerRadius = 20f
        cardView.background = gradientDrawable
        loadTotalValues()
        acceptButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val period = periodEditText.text.toString().toIntOrNull()
            val percent = percentEditText.text.toString().toDoubleOrNull()
            val amount = amountEditText.text.toString().toDoubleOrNull()

            if (name.isNotEmpty() && period != null && percent != null && amount != null) {
                val profit = calculateProfit(amount, percent, period)


                val homeFragment = HomeFragment()
                val bundle = Bundle()
                bundle.putString("name", name)
                bundle.putString("period", period.toString())
                bundle.putString("percent", percent.toString())
                bundle.putString("amount", amount.toString())
                bundle.putString("profit", profit.toString())
                homeFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        return view
    }

    private fun loadTotalValues() {
        val sharedPreferences = requireContext().getSharedPreferences("totals", Context.MODE_PRIVATE)
        totalPortfolio = sharedPreferences.getFloat("totalPortfolio", 0f).toDouble()
        totalProfit = sharedPreferences.getFloat("totalProfit", 0f).toDouble()

        totalPortfolioTextView.text = "$totalPortfolio ₽"
        totalProfitTextView.text = "$totalProfit ₽"
    }


    private fun calculateProfit(amount: Double, percent: Double, period: Int): Double {
        return amount * (percent / 100) * (period / 12.0)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}