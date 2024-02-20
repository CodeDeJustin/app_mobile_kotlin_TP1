package com.example.tp1_m14_justin_allard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tp1_m14_justin_allard.databinding.ActivitySecondBinding
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private lateinit var tvMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvMessage = findViewById(R.id.tvMessage)

        val fullName = intent.getStringExtra("fullName")
        val age = intent.getIntExtra("age", 0)
        val numberOfCats = intent.getIntExtra("numberOfCats", 0)
        val numberOfDogs = intent.getIntExtra("numberOfDogs", 0)

        val animalMessage = when {
            numberOfCats > numberOfDogs -> "and are a cat person"
            numberOfCats < numberOfDogs -> "and are a dog person"
            numberOfCats == 0 && numberOfDogs == 0 -> "and you can buy a parrot because you're alone"
            else -> "and are a dog and cat person"
        }

        tvMessage.text = "Hello $fullName. You are $age years old, $animalMessage."

        val backButton = binding.bBack
        backButton.setOnClickListener {
            finish()
        }
    }
}
