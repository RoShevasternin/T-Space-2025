package com.brbadenka.activnoeble

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val home = findViewById<ImageView>(R.id.iconHome)
        val history = findViewById<ImageView>(R.id.iconSovet)
        val plus = findViewById<ImageView>(R.id.iconPlus)
        history.setImageResource(R.drawable.his_wh)
        plus.setImageResource(R.drawable.plus_wh)
        home.setImageResource(R.drawable.home_gr)
        loadFragment(HomeFragment())
        findViewById<ImageView>(R.id.iconHome).setOnClickListener {
            home.setImageResource(R.drawable.home_gr)
            history.setImageResource(R.drawable.his_wh)
            plus.setImageResource(R.drawable.plus_wh)
            loadFragment(HomeFragment())
        }

        findViewById<ImageView>(R.id.iconPlus).setOnClickListener {
            home.setImageResource(R.drawable.home_wh)
            plus.setImageResource(R.drawable.plus_gr)
            history.setImageResource(R.drawable.his_wh)
            loadFragment(PlusFragment())
        }

        findViewById<ImageView>(R.id.iconSovet).setOnClickListener {
            home.setImageResource(R.drawable.home_wh)
            history.setImageResource(R.drawable.his_gr)
            plus.setImageResource(R.drawable.plus_wh)
            loadFragment(HisFragment())
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
    }


    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}