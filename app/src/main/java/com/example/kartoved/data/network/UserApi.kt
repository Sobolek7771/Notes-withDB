package com.example.kartoved.data.network

import com.example.kartoved.model.LoginPassword
import com.example.kartoved.model.User
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("api/v1/users/auth/sign_in/")
    suspend fun singIn(
        @Body logPass: LoginPassword
    ): Response<User>

    @GET("api/v1/users/auth/sign_out/")
    suspend fun signOut(
        @Header("Cookie") cookies: String
    ): Response<Any>
}