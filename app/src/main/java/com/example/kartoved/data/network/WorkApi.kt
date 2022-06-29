package com.example.kartoved.data.network

import com.example.kartoved.model.Work
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface WorkApi {

    @GET("api/v1/work/")
    suspend fun getWork(
        @Header("Cookie") cookies: String
    ):Response<ArrayList<Work>>
}