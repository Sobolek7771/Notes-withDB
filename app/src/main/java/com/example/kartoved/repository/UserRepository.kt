package com.example.kartoved.repository

import androidx.lifecycle.LiveData
import com.example.kartoved.data.dao.UserDao
import com.example.kartoved.data.network.RetrofitInstance
import com.example.kartoved.model.LoginPassword
import com.example.kartoved.model.User
import retrofit2.Response

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<User> = userDao.readAllData()

    suspend fun singIn(loginPassword: LoginPassword): Response<User>{
        return RetrofitInstance.userApi.singIn(loginPassword)
    }

    suspend fun signOut(cookies: String): Response<Any>{
        return RetrofitInstance.userApi.signOut(cookies)
    }

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun deleteAllUser(){
        userDao.deleteAllUsers()
    }
}