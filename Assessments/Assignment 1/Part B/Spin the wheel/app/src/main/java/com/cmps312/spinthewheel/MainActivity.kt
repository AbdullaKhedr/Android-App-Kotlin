package com.cmps312.spinthewheel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val winners = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNameBtn.setOnClickListener {
            if (nameEdt.text.isNotEmpty()) {
                val name = nameEdt.text.toString()
                winners.add(name)
                Toast.makeText(this, "$name has been added", Toast.LENGTH_SHORT).show()
                nameEdt.text = null
            } else
                Toast.makeText(this, "Please add a name first", Toast.LENGTH_SHORT).show()
        }

        spinBtn.setOnClickListener {
            if (winners.isNotEmpty()) {
                var i = Random.nextInt(0, winners.size)
                winnerEdt.text = "The Winner is ${winners[i]}"
            } else {
                Toast.makeText(this, "No names to choose from!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}