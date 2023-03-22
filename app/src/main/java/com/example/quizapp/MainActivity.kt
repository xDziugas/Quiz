package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var startQuizFlags: TextView
    private lateinit var startQuizCapital: TextView
    private lateinit var startQuiz1: TextView
    private lateinit var startQuiz2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startQuizFlags = findViewById(R.id.tv_start_quiz_flags)
        startQuizCapital = findViewById(R.id.tv_start_quiz_capital)
        startQuiz1 = findViewById(R.id.tv_start_quiz_1)
        startQuiz2 = findViewById(R.id.tv_start_quiz_2)

        startQuizFlags.setOnClickListener(this)
        startQuizCapital.setOnClickListener(this)
        startQuiz1.setOnClickListener(this)
        startQuiz2.setOnClickListener(this)

    }
    override fun onClick(v: View?){
        when(v?.id){
            R.id.tv_start_quiz_flags ->{
                val intent = Intent(this, SelectContinentActivity::class.java)
                intent.putExtra(Constants.QUIZ_QUESTION, 0)
                startActivity(intent)
                finish()
            }

            R.id.tv_start_quiz_capital ->{
                val intent = Intent(this, SelectContinentActivity::class.java)
                intent.putExtra(Constants.QUIZ_QUESTION, 1)
                startActivity(intent)
                finish()
            }

            R.id.tv_start_quiz_1 ->{
                val intent = Intent(this, SelectContinentActivity::class.java)
                intent.putExtra(Constants.QUIZ_QUESTION, 2)
                startActivity(intent)
                finish()
            }

            R.id.tv_start_quiz_2 ->{
                val intent = Intent(this, SelectContinentActivity::class.java)
                intent.putExtra(Constants.QUIZ_QUESTION, 3)
                startActivity(intent)
                finish()
            }
        }

    }
}