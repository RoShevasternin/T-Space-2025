package com.finakaklem.manderinka

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val home = findViewById<ImageView>(R.id.iconHome)
        val history = findViewById<ImageView>(R.id.iconSovet)
        history.setImageResource(R.drawable.history_white)
        home.setImageResource(R.drawable.home_green)
        val bottomBar = findViewById<ConstraintLayout>(R.id.bottom_bar)

        loadFragment(HomeFragment())

        findViewById<ImageView>(R.id.iconHome).setOnClickListener {
            home.setImageResource(R.drawable.home_green)
            history.setImageResource(R.drawable.history_white)
            loadFragment(HomeFragment())
        }

        findViewById<ImageView>(R.id.iconPlus).setOnClickListener {
            home.setImageResource(R.drawable.home_white)
            history.setImageResource(R.drawable.history_white)
            loadFragment(PlusFragment())
        }

        findViewById<ImageView>(R.id.iconSovet).setOnClickListener {
            home.setImageResource(R.drawable.home_white)
            history.setImageResource(R.drawable.history_green)
            loadFragment(HistoryFragment())
        }
    }


    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}