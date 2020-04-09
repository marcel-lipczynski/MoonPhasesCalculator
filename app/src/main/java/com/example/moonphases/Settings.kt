package com.example.moonphases

import android.app.Activity
import android.content.Intent
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
        val extras = intent.extras ?: return
        algorithmSettings = extras.getString("algorithm").toString()
        hemisphereSettings = extras.getString("hemisphere").toString()
    }

    fun onClickHemisphereButton(view: View) {
        val clickedButton: RadioButton = findViewById(hemisphereSelect.checkedRadioButtonId)
        hemisphereSettings = clickedButton.text.toString()
        Toast.makeText(
            applicationContext, "Wybrana półkula: ${clickedButton.text}",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onClickAlgorithmButton(view: View) {
        val clickedButton: RadioButton = findViewById(algorithmSelect.checkedRadioButtonId)
        algorithmSettings = clickedButton.text.toString()
        Toast.makeText(
            applicationContext, "Wybrany algorytm: ${clickedButton.text}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun finish() {
        val data = Intent()

        data.putExtra("hemisphere", hemisphereSettings)
        data.putExtra("algorithm", algorithmSettings)

        setResult(Activity.RESULT_OK, data)

        super.finish()

    }

}


