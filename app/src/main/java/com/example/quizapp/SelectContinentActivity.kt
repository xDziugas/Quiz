package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SelectContinentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var continentEurope: TextView
    private lateinit var continentAsia: TextView
    private lateinit var continentAfrica: TextView
    private lateinit var continentWorld: TextView
    var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_continent)

        continentEurope = findViewById(R.id.tv_select_europe)
        continentAsia = findViewById(R.id.tv_select_asia)
        continentAfrica = findViewById(R.id.tv_select_africa)
        continentWorld = findViewById(R.id.tv_select_world)

        number = intent.getIntExtra(Constants.QUIZ_QUESTION, 0)

        continentEurope.setOnClickListener(this)
        continentAsia.setOnClickListener(this)
        continentAfrica.setOnClickListener(this)
        continentWorld.setOnClickListener(this)

    }

    override fun onClick(v: View?){
        when(v?.id){
            R.id.tv_select_europe ->{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.QUIZ_CONTINENT, 0)
                intent.putExtra(Constants.QUIZ_QUESTION, number)
                startActivity(intent)
                finish()
            }

            R.id.tv_select_asia ->{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.QUIZ_CONTINENT, 1)
                intent.putExtra(Constants.QUIZ_QUESTION, number)
                startActivity(intent)
                finish()
            }

            R.id.tv_select_africa ->{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.QUIZ_CONTINENT, 2)
                intent.putExtra(Constants.QUIZ_QUESTION, number)
                startActivity(intent)
                finish()
            }

            R.id.tv_select_world ->{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.QUIZ_CONTINENT, 3)
                intent.putExtra(Constants.QUIZ_QUESTION, number)
                startActivity(intent)
                finish()
            }
        }

    }
}