package com.example.quizapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mQuestionsData = ArrayList<MyDataItem>()
    private var mSelectedPosition: Int = 0
    private var answerChecked: Boolean = false
    private var mCorrectAnswers: Int = 0
    private var mQuizQuestion: Int = 0
    private val mTotalQuestions: Int = 10
    private var correctAnswer: Int = 1
    private var optionsArray = ArrayList<TextView>()
    private var mQuizContinent: Int = 0

    private var mQuestionsContinent = ArrayList<MyDataItem>()


    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var ivImage: ImageView
    private lateinit var btnSubmit: Button
    private lateinit var tvCountryName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mQuestionsList = Constants.getQuestions()

        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        progressBar.max = mTotalQuestions
        tvCountryName = findViewById(R.id.tv_county_name)

        optionsArray.add(tvOptionOne)
        optionsArray.add(tvOptionTwo)
        optionsArray.add(tvOptionThree)
        optionsArray.add(tvOptionFour)

        //Thread.sleep(1000)
        getMyData()

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

    }

    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<ArrayList<MyDataItem>> {
            override fun onResponse(
                call: Call<ArrayList<MyDataItem>>,
                response: Response<ArrayList<MyDataItem>>
            ) {

                 if(response.isSuccessful){
                    val rawList = response.body()


                     if (rawList != null) {
                         mQuestionsData.addAll(rawList)
                     }

                     if(mQuestionsData.size != 0){

                         mQuestionsData.shuffle()

                         mQuizQuestion = intent.getIntExtra(Constants.QUIZ_QUESTION, 0)
                         mQuizContinent = intent.getIntExtra(Constants.QUIZ_CONTINENT, 0)

                         setQuestion()
                     }
                }
            }

            override fun onFailure(call: Call<ArrayList<MyDataItem>?>, t: Throwable) {
                for(mData in 0..1){
                    Log.d("MainActivity", "Error")
                }
            }
        })
    }

    private fun setQuestion(){

        answerChecked = false
        val question = mQuestionsList!![mQuizQuestion]

        defaultOptionsView()

        if(mQuizContinent == 0){
            for(i in 0 until mQuestionsData.size) {
                if(mQuestionsData[i].region == "Europe" && mQuestionsData[i].capital.isNotEmpty()){
                    mQuestionsContinent.add(mQuestionsData[i])
                }
            }
        }

        if(mQuizContinent == 1){
            for(i in 0 until mQuestionsData.size) {
                if(mQuestionsData[i].region == "Asia" && mQuestionsData[i].capital != null){
                    mQuestionsContinent.add(mQuestionsData[i])
                }
                Log.d(TAG, "setQuestion: " + mQuestionsData[i].region + ", capital: " + mQuestionsData[i].capital)
            }
        }

        if(mQuizContinent == 2){
            for(i in 0 until mQuestionsData.size) {
                if(mQuestionsData[i].region == "Africa" && mQuestionsData[i].capital.isNotEmpty()){
                    mQuestionsContinent.add(mQuestionsData[i])
                }
            }
        }

        if(mCurrentPosition == mTotalQuestions){
            btnSubmit.text = getString(R.string.text_finish)
        }else{
            btnSubmit.text = getString(R.string.text_submit)
        }

        progressBar.progress = mCurrentPosition
        ("$mCurrentPosition" + "/" + progressBar.max).also { tvProgress.text = it }

        tvQuestion.text = question.question

        if(mQuizContinent == 3){
            Glide.with(this)
                .load(mQuestionsData[mCurrentPosition-1].flags.png)
                .into(ivImage)
        }else{
            Glide.with(this)
                .load(mQuestionsContinent[mCurrentPosition-1].flags.png)
                .into(ivImage)
        }


        if(mQuizQuestion != 0){
            if(mQuizContinent == 3){
                tvCountryName.text = mQuestionsData[mCurrentPosition-1].name
            }else{
                tvCountryName.text = mQuestionsContinent[mCurrentPosition-1].name
            }
            tvCountryName.visibility = VISIBLE
        }

        //add loading screen
        // todo: what if fetching fails, add handler

        val random = Random()
        val randomOptions = IntArray(4)
        randomOptions[0] = mCurrentPosition-1

        if(mQuizContinent == 3){
            for(i in 1..3){
                var randomInt = random.nextInt(mQuestionsData.size-1) //Countries from the past questions don't repeat

                while(randomOptions.contains(randomInt) || mQuestionsData[randomInt].capital.isEmpty()){
                    randomInt = random.nextInt(mQuestionsData.size-1)
                }

                randomOptions[i] = randomInt
            }
        }else{
            for(i in 1..3){
                var randomInt = random.nextInt(mQuestionsContinent.size-1) //Countries from the past questions doesn't repeat

                while(randomOptions.contains(randomInt) || mQuestionsContinent[randomInt].capital.isEmpty()){
                    randomInt = random.nextInt(mQuestionsContinent.size-1)
                }

                randomOptions[i] = randomInt
            }
        }


        randomOptions.shuffle()

        correctAnswer = randomOptions.indexOf(mCurrentPosition-1)

        for(i in 0..3){
            when(mQuizQuestion){
                0 ->{
                    if(mQuizContinent == 3){
                        optionsArray[i].text = mQuestionsData[randomOptions[i]].name
                    }else{
                        optionsArray[i].text = mQuestionsContinent[randomOptions[i]].name
                    }
                }

                1 ->{
                    if(mQuizContinent == 3){
                        optionsArray[i].text = mQuestionsData[randomOptions[i]].capital
                    }else{
                        optionsArray[i].text = mQuestionsContinent[randomOptions[i]].capital
                    }
                }

                2 ->{
                    if(mQuizContinent == 3){
                        optionsArray[i].text = mQuestionsData[randomOptions[i]].area.toString()
                    }else{
                        optionsArray[i].text = mQuestionsContinent[randomOptions[i]].area.toString()
                    }
                }

                3 ->{
                    if(mQuizContinent == 3){
                        optionsArray[i].text = mQuestionsData[randomOptions[i]].population.toString()
                    }else{
                        optionsArray[i].text = mQuestionsContinent[randomOptions[i]].population.toString()
                    }
                }
            }
        }
    }

    private fun defaultOptionsView(){

        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.tv_option_one ->{
                if(!answerChecked)
                    selectedOptionView(tvOptionOne, selectedOptionNum = 1)
            }

            R.id.tv_option_two ->{
                if(!answerChecked)
                    selectedOptionView(tvOptionTwo, selectedOptionNum = 2)
            }

            R.id.tv_option_three ->{
                if(!answerChecked)
                    selectedOptionView(tvOptionThree, selectedOptionNum = 3)
            }

            R.id.tv_option_four ->{
                if(!answerChecked)
                    selectedOptionView(tvOptionFour, selectedOptionNum = 4)
            }

            R.id.btn_submit ->{
                answerChecked = true
                if(mSelectedPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mTotalQuestions ->{
                            setQuestion()
                        }else ->{
                            val intent = Intent(this, ResultActivity::class.java)

                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mTotalQuestions)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{

                    if(correctAnswer + 1 != mSelectedPosition){
                        answerView(mSelectedPosition, R.drawable.incorrect_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(correctAnswer + 1, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mTotalQuestions){
                        btnSubmit.text = getString(R.string.text_finish)
                    }else{
                        btnSubmit.text = getString(R.string.text_next_question)
                    }
                    mSelectedPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){

        when(answer){
            1 ->{
                tvOptionOne.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            2 ->{
                tvOptionTwo.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            3 ->{
                tvOptionThree.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }

            4 ->{
                tvOptionFour.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

    }
}