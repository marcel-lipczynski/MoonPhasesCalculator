package com.example.moonphases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {

    var algorithmSettings = "trig1"
    var hemisphereSettings = "N"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun onClickHemisphereButton(view: View) {
        val clickedButton: RadioButton = findViewById(hemisphereSelect.checkedRadioButtonId)
        hemisphereSettings = clickedButton.text.toString()
        Toast.makeText(
            applicationContext, "Chosen hemisphere : ${clickedButton.text}",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onClickAlgorithmButton(view: View) {
        val clickedButton: RadioButton = findViewById(algorithmSelect.checkedRadioButtonId)
        algorithmSettings = clickedButton.text.toString()
        Toast.makeText(
            applicationContext, "Chosen algorithm : ${clickedButton.text}",
            Toast.LENGTH_SHORT
        ).show()
    }

}


