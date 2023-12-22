package com.example.trainingmemory

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MemoryTrainingSecondActivity : AppCompatActivity() {

    private lateinit var textViewWord: TextView
    private lateinit var radioButtonOption1: RadioButton
    private lateinit var radioButtonOption2: RadioButton
    private lateinit var radioButtonOption3: RadioButton
    private lateinit var radioButtonOption4: RadioButton
    private lateinit var radioButtonOption5: RadioButton
    private lateinit var buttonNext: Button
    private lateinit var radioGroupWords: RadioGroup // Объявление переменной


    private val words = listOf(
        Pair("Яблоко", "Груша"),
        Pair("Кошка", "Собака"),
        Pair("Стул", "Стол"),
        Pair("Солнце", "Луна"),
        Pair("Вода", "Огонь"),
        Pair("Книга", "Ручка"),
        Pair("Обувь", "Носок"),
        Pair("Дерево", "Лист"),
        Pair("Пицца", "Паста"),
        Pair("Гитара", "Барабан"),
        Pair("Океан", "Река"),
        Pair("Гора", "Долина"),
        Pair("Кофе", "Чай"),
        Pair("Телефон", "Компьютер"),
        Pair("Автомобиль", "Велосипед"),
        Pair("Ключ", "Замок"),
        Pair("Лошадь", "Корова"),
        Pair("Звезда", "Планета"),
        Pair("Дождь", "Снег"),
        Pair("Рыба", "Птица"),
        Pair("Нож", "Вилка"),
        Pair("Шляпа", "Перчатки"),
        Pair("Поезд", "Автобус"),
        Pair("Часы", "Часы (наручные)"),
        Pair("Камера", "Фото"),
        Pair("Деньги", "Кошелек"),
        Pair("Ложка", "Тарелка"),
        Pair("Молоко", "Сок"),
        Pair("Слон", "Лев"),
        Pair("Доктор", "Медсестра"),
        Pair("Футбол", "Баскетбол"),
        Pair("Муравей", "Пчела"),
        Pair("Стакан", "Кружка"),
        Pair("Орех", "Фундук"),
        Pair("Огурец", "Помидор"),
        Pair("Капуста", "Брокколи"),
        Pair("Банан", "Апельсин"),
        Pair("Лимон", "Лайм"),
        Pair("Город", "Деревня"),
        Pair("Газета", "Журнал"),
        Pair("Скрипка", "Гитара"),
        Pair("Билет", "Паспорт"),
        Pair("Подушка", "Одеяло"),
        Pair("Сумка", "Рюкзак"),
        Pair("Пианино", "Гитара"),
        Pair("Туфли", "Кроссовки"),
        Pair("Футболка", "Рубашка"),
        Pair("Кепка", "Шапка"),
        Pair("Рука", "Нога"),
        Pair("Плечо", "Локоть"),
        Pair("Колено", "Голень")
        // Можно добавить больше слов, если нужно
    )


    private var currentPairIndex = 0
    private var lives = 3 // Инициализация количества жизней

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memory_training_cube_activity)

        textViewWord = findViewById(R.id.textViewWord)
        radioButtonOption1 = findViewById(R.id.radioButtonOption1)
        radioButtonOption2 = findViewById(R.id.radioButtonOption2)
        radioButtonOption3 = findViewById(R.id.radioButtonOption3)
        radioButtonOption4 = findViewById(R.id.radioButtonOption4)
        radioButtonOption5 = findViewById(R.id.radioButtonOption5)

        buttonNext = findViewById(R.id.buttonNext)
        radioGroupWords = findViewById(R.id.radioGroupWords)

        updateLivesDisplay() // Обновление отображения жизней

        displayWords()

        buttonNext.setOnClickListener {
            checkAnswer()
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            finish() // Возвращаемся назад при нажатии FloatingActionButton
        }
    }

    private fun displayWords() {
        if (currentPairIndex < words.size) {
            val currentPair = words[currentPairIndex]
            textViewWord.text = currentPair.first

            val options = mutableListOf<String>()
            options.add(currentPair.second) // Добавляем правильный ответ

            while (options.size < 5) { // Добавляем еще 4 случайных слова
                val randomWord = getRandomWord(currentPairIndex)
                if (!options.contains(randomWord)) {
                    options.add(randomWord)
                }
            }

            options.shuffle()

            radioButtonOption1.text = options[0]
            radioButtonOption2.text = options[1]
            radioButtonOption3.text = options[2]
            radioButtonOption4.text = options[3]
            radioButtonOption5.text = options[4]

            radioButtonOption1.isChecked = false
            radioButtonOption2.isChecked = false
            radioButtonOption3.isChecked = false
            radioButtonOption4.isChecked = false
            radioButtonOption5.isChecked = false
        } else {
            // Все слова пройдены, показываем "ПОБЕДА"
            textViewWord.text = "ПОБЕДА"

            // Переход на MainActivity через 3 секунды
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Закрытие текущей активности
            }, 3000) // Ждем 3 секунды перед переходом на MainA
        }
    }



    private fun checkAnswer() {
        val correctPair = words[currentPairIndex]
        val checkedRadioButtonId = radioGroupWords.checkedRadioButtonId
        val checkedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
        val selectedWord = checkedRadioButton?.text?.toString()

        if (selectedWord == correctPair.second) {
            // Логика правильного ответа
        } else {
            lives--
            updateLivesDisplay()

            if (lives <= 0) {
                // Здесь все жизни исчерпаны, переход на MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Закрытие текущей активности
            }
        }

        currentPairIndex++
        displayWords()
    }

    private fun getRandomWord(excludeIndex: Int): String {
        val filteredWords = words.filterIndexed { index, _ -> index != excludeIndex }
        return filteredWords.shuffled().first().second
    }

    private fun updateLivesDisplay() {
        // Обновление отображения количества жизней
        val livesTextView: TextView = findViewById(R.id.textViewLives)
        livesTextView.text = "Lives: $lives"
    }

}

