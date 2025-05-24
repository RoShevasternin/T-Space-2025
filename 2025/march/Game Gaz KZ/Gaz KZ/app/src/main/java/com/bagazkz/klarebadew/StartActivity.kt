package com.bagazkz.klarebadew

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bagazkz.klarebadew.util.log
import kotlin.jvm.java

private var counter = 0

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        counter++
        log("Start onCreate: $counter")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}