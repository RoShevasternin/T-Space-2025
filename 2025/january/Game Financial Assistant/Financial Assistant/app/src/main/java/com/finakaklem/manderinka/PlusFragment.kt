package com.finakaklem.manderinka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment

class PlusFragment : Fragment() {

    private lateinit var nameEditText: EditText
    private lateinit var periodEditText: EditText
    private lateinit var percentEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var acceptButton: ImageButton
    private lateinit var deleteButton: ImageButton

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
