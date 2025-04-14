package com.example.mathgame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var layout: ConstraintLayout
    private lateinit var textViewExpressao: TextView
    private lateinit var editTextResposta: EditText
    private lateinit var buttonEnviar: Button
    private lateinit var textViewFeedback: TextView

    private var currentQuestion = 0
    private var score = 0
    private val totalQuestions = 5
    private val expressions = mutableListOf<Pair<String, Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        layout = findViewById(R.id.layoutJogo)
        textViewExpressao = findViewById(R.id.textViewExpressao)
        editTextResposta = findViewById(R.id.editTextResposta)
        buttonEnviar = findViewById(R.id.buttonEnviar)
        textViewFeedback = findViewById(R.id.textViewFeedback)

        generateExpressions()
        showQuestion()

        buttonEnviar.setOnClickListener { checkAnswer() }
    }

    private fun generateExpressions() {
        while (expressions.size < totalQuestions) {
            val num1 = Random.nextInt(0, 100)
            val num2 = Random.nextInt(0, 100)
            val op = if (Random.nextBoolean()) "+" else "-"
            val valid = op == "+" || (op == "-" && num1 >= num2)
            if (valid) {
                val result = if (op == "+") num1 + num2 else num1 - num2
                expressions.add("$num1 $op $num2" to result)
            }
        }
    }

    private fun showQuestion() {
        layout.setBackgroundColor(Color.WHITE)
        textViewFeedback.text = ""
        editTextResposta.setText("")
        val (expr, _) = expressions[currentQuestion]
        textViewExpressao.text = "Expressão ${currentQuestion + 1} de $totalQuestions:\n$expr"
    }

    private fun checkAnswer() {
        val (_, correct) = expressions[currentQuestion]
        val userAnswer = editTextResposta.text.toString().toIntOrNull()

        if (userAnswer == correct) {
            layout.setBackgroundColor(Color.GREEN)
            score += 20
            textViewFeedback.text = "Correto!"
        } else {
            layout.setBackgroundColor(Color.RED)
            textViewFeedback.text = "Incorreto! Resposta correta: $correct"
        }

        currentQuestion++
        if (currentQuestion < totalQuestions) {
            buttonEnviar.text = "Próxima"
            buttonEnviar.setOnClickListener {
                showQuestion()
                resetButton()
            }
        } else {
            buttonEnviar.text = "Finalizar"
            buttonEnviar.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", score)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun resetButton() {
        buttonEnviar.text = "Responder"
        buttonEnviar.setOnClickListener { checkAnswer() }
    }
}