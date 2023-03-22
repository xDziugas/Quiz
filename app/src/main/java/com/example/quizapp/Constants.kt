package com.example.quizapp

import android.util.Log
import android.widget.Toast
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

object Constants {

    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"
    const val QUIZ_QUESTION: String = "question_number"
    const val QUIZ_CONTINENT: String = "quiz_continent"

    fun getQuestions(): ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val que1 = Question(1,
            "Which country does this flag belong to?",
        )
        questionsList.add(que1)

        val que2 = Question(2,
            "This is the capital of which country?",
        )
        questionsList.add(que2)

        val que3 = Question(3,
            "What is the area of the following country?",
        )
        questionsList.add(que3)

        val que4 = Question(4,
            "What is the population of the following country?",
        )
        questionsList.add(que4)

        return questionsList
    }

}