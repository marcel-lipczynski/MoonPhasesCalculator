package com.example.moonphases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_full_moon_list.*

class FullMoonList : AppCompatActivity() {

    private var usedAlgorithm: String = "Trig1"
    private var startingYear = "1900"
    private var listItems = arrayListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moon_list)

        val extras = intent.extras ?: return
        usedAlgorithm = extras.getString("algorithm").toString()
        val currentlyUsedAlgorithm = "Aktualnie używany algorytm: $usedAlgorithm"
        algorithmInfo.text = currentlyUsedAlgorithm
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        fullMoonList.adapter = adapter
    }

    fun onClickAddYearButton(view: View) {

        val currentValue = yearInput.text.toString()
        if (currentValue == "") {
            yearInput.setText(startingYear)
        } else if (Integer.parseInt(currentValue) == 2200) {
            return
        } else {
            yearInput.setText((Integer.parseInt(currentValue) + 1).toString())
        }

    }

    fun onClickSubtractYearButton(view: View) {

        val currentValue = yearInput.text.toString()
        if (currentValue == "") {
            yearInput.setText(startingYear)
        } else if (Integer.parseInt(currentValue) == 1900) {
            return
        } else {
            yearInput.setText((Integer.parseInt(currentValue) - 1).toString())

        }

    }

    fun addItem(view: View){
        
        listItems.add(startingYear)
        adapter.notifyDataSetChanged()
    }


}
