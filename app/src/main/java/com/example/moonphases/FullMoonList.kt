package com.example.moonphases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FullMoonList : AppCompatActivity() {

    private var usedAlgorithm: String = "Trig1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moon_list)

        val extras = intent.extras ?: return
        usedAlgorithm = extras.getString("algorithm").toString()

    }


}
