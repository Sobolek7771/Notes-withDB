package com.example.kartoved.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://bit4u.ru:8000")
            //.baseUrl("https://a058-176-59-212-2.eu.ngrok.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val workApi: WorkApi by lazy{
        retrofit.create(WorkApi::class.java)
    }
}