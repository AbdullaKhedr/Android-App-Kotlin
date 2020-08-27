package com.cmps312.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener {
            resulsTv1.text =
                (num1Edt.text.toString().toInt() + num2Edt.text.toString().toInt()).toString()
        }
        subBtn.setOnClickListener {
            resulsTv1.text =
                (num1Edt.text.toString().toInt() - num2Edt.text.toString().toInt()).toString()
        }
        mutiBtn.setOnClickListener {
            resulsTv1.text =
                (num1Edt.text.toString().toInt() * num2Edt.text.toString().toInt()).toString()
        }
        divBtn.setOnClickListener {
            resulsTv1.text =
                (num1Edt.text.toString().toInt() / num2Edt.text.toString().toInt()).toString()
        }
    }
}