package com.example.kartoved.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.kartoved.data.database.KartovedDatabase
import com.example.kartoved.model.LoginPassword
import com.example.kartoved.model.User
import com.example.kartoved.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    //network
    var userResponse: MutableLiveData<Response<User>> = MutableLiveData()
    val userSignOutResponse: MutableLiveData<Response<Any>> = MutableLiveData()
    //database
    val readAllData: LiveData<User>

    init {
        val userDao = KartovedDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUserInDatabase(user: User){
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

    fun deleteAllUsersFromDatabase(){
        viewModelScope.launch{
            repository.deleteAllUser()
        }
    }

    fun signIn(loginPassword: LoginPassword){
        viewModelScope.launch{
            val response = repository.singIn(loginPassword)
            userResponse.value = response
        }
    }

    fun signOut(cookies: String){
        viewModelScope.launch {
            val response = repository.signOut(cookies)
            userSignOutResponse.value = response
        }
    }
}