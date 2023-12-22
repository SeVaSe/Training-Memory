package com.example.trainingmemory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var firstGameButton: Button
    private lateinit var secondGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstGameButton = findViewById(R.id.FirstGame)
        secondGameButton = findViewById(R.id.SecondGame)

        firstGameButton.setOnClickListener {
            startActivity(Intent(this, MemoryTrainingFirstActivity::class.java))
        }

        secondGameButton.setOnClickListener {
            try {
                startActivity(Intent(this, MemoryTrainingSecondActivity::class.java))
            } catch (e: Exception) {
                Log.e("MemoryTrainingCubeActivity", "Ошибка при переходе в активность MemoryTrainingCubeActivity: ${e.message}")
            }
        }
    }
}