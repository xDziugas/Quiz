package com.example.quizapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("all")
    fun getData(): Call<ArrayList<MyDataItem>>
}