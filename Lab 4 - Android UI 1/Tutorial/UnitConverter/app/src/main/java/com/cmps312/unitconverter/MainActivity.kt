package com.cmps312.unitconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn.setOnClickListener {
            if (currencyConverterRb.isChecked) {
                val intent = Intent(this, CurrencyConverterActivity::class.java)
                startActivity(intent)
            } else if (bmiCaculatorRb.isChecked) {
                val intent = Intent(this, BMICalculatorActivity::class.java)
                startActivity(intent)
            } else {
                val text = "Please choose one option first"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }
    }
}