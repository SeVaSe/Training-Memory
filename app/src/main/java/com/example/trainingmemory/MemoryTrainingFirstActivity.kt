package com.example.trainingmemory

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.trainingmemory.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Random

class MemoryTrainingFirstActivity : AppCompatActivity() {

    private lateinit var levelTextView: TextView
    private lateinit var displayTextView: TextView
    private lateinit var infoTextView: TextView
    private lateinit var inputEditText: EditText
    private lateinit var checkButton: Button

    private val random = Random()
    private val numberSequence = mutableListOf<Int>()
    private val handler = Handler()

    private var currentIndex = 0
    private var attempts = 0
    private var count = 3
    private var level = 1 // Уровень игры

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_training_activity)

        levelTextView = findViewById(R.id.levelTextView)
        displayTextView = findViewById(R.id.displayTextView)
        inputEditText = findViewById(R.id.inputEditText)
        checkButton = findViewById(R.id.checkButton)
        infoTextView = findViewById(R.id.infoTextView) // Инициализируем infoTextView здесь


        checkButton.setOnClickListener {
            checkSequence()
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            finish() // Возвращаемся назад при нажатии FloatingActionButton
        }

        updateLevelText()
        generateAndDisplaySequence()
    }

    private fun updateLevelText() {
        levelTextView.text = "Level: $level"
    }

    private fun generateAndDisplaySequence() {
        inputEditText.isEnabled = false // Блокировать поле ввода
        checkButton.isEnabled = false // Блокировать кнопку

        numberSequence.clear()
        val sequenceLength = level + 3 // Длина последовательности соответствует уровню + 3
        for (i in 0 until sequenceLength) {
            val randomNumber = random.nextInt(MAX_NUMBER) + 1
            numberSequence.add(randomNumber)
            displayNumberWithDelay(randomNumber, i, sequenceLength)
        }
    }

    private fun displayNumberWithDelay(number: Int, position: Int, sequenceLength: Int) {
        val handler = Handler()
        handler.postDelayed({
            displayTextView.text = number.toString()
            if (position == sequenceLength - 1) {
                Handler().postDelayed({
                    displayTextView.text = ""
                    inputEditText.isEnabled = true // Разблокировать поле ввода после показа чисел
                    checkButton.isEnabled = true // Сделать кнопку активной
                }, DISPLAY_DURATION)
            }
        }, DISPLAY_DURATION * (position + 1))
    }

    private fun checkSequence() {
        val userInput = inputEditText.text.toString()
        val concatenatedSequence = numberSequence.joinToString("")

        if (userInput == concatenatedSequence) {
            attempts = 0 // Сбросить счетчик попыток после правильного ответа
            level++ // Увеличить уровень
            updateLevelText()
            generateAndDisplaySequence()
            inputEditText.text.clear()
        } else {
            attempts++
            if (attempts >= MAX_ATTEMPTS) {
                infoTextView.text = "Достигнуто максимальное количество попыток"
                inputEditText.isEnabled = false
                checkButton.isEnabled = false

                // После заданной задержки переходим на MainActivity
                handler.postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Закрываем текущую активность
                }, DELAY_BEFORE_MAIN_ACTIVITY)
            } else {
                count--
                infoTextView.text = "Попробуйте еще раз, у вас осталось попыток: " + count

            }
        }
    }

    companion object {
        private const val MAX_NUMBER = 9 // Однозначные числа
        private const val DISPLAY_DURATION: Long = 1500 // in milliseconds
        private const val MAX_ATTEMPTS = 3
        private const val DELAY_BEFORE_MAIN_ACTIVITY = 3000L // 3 секунды задержки
    }
}