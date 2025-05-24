package com.traoya.yatirimya

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.traoya.yatirimya.util.log

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