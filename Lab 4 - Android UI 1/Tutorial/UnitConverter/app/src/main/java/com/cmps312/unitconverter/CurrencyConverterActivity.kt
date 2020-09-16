package com.cmps312.unitconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_currency_converter.*

class CurrencyConverterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)

        convertQrBtn.setOnClickListener {
            if (qrEdt.text.isEmpty()) {
                val text = "No amount to be converted"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            } else {
                val qrAmount = qrEdt.text.toString().toInt()
                val qrInUsd = qrAmount * 3.64
                usdEdt.setText("$qrInUsd")
            }
        }

        convertUSDBtn.setOnClickListener {
            if (usdEdt.text.isEmpty()) {
                val text = "No amount to be converted"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            } else {
                val usdAmount = usdEdt.text.toString().toInt()
                val usdInqr = usdAmount / 3.64
                qrEdt.setText("$usdInqr")
            }
        }
    }
}