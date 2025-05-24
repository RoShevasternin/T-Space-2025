package com.goskapmepdup.dalaylamek

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.goskapmepdup.dalaylamek.other.AddInvestFragment
import com.goskapmepdup.dalaylamek.other.GeneralMenuFragment
import com.goskapmepdup.dalaylamek.other.HistoryInvestFragment

class MenuMainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_main2)
        val home = findViewById<ImageView>(R.id.iconHome)
        val history = findViewById<ImageView>(R.id.iconSovet)
        history.setImageResource(R.drawable.history_white)
        home.setImageResource(R.drawable.home_green)

        opneFragment(GeneralMenuFragment())

        findViewById<ImageView>(R.id.iconHome).setOnClickListener {
            home.setImageResource(R.drawable.home_green)
            history.setImageResource(R.drawable.history_white)
            opneFragment(GeneralMenuFragment())
        }

        findViewById<ImageView>(R.id.iconPlus).setOnClickListener {
            home.setImageResource(R.drawable.home_white)
            history.setImageResource(R.drawable.history_white)
            opneFragment(AddInvestFragment())
        }

        findViewById<ImageView>(R.id.iconSovet).setOnClickListener {
            home.setImageResource(R.drawable.home_white)
            history.setImageResource(R.drawable.history_green)
            opneFragment(HistoryInvestFragment())
        }
    }


    private fun opneFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}