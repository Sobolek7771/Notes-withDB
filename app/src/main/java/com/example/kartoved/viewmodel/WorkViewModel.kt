package com.example.kartoved.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kartoved.data.database.KartovedDatabase
import com.example.kartoved.model.Work
import com.example.kartoved.repository.WorkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class WorkViewModel(application: Application): AndroidViewModel(application) {

    private val repository: WorkRepository

    //network
    var workResponse: MutableLiveData<Response<ArrayList<Work>>> = MutableLiveData()
    //database
    val readAllData: LiveData<Work>

    init {
        val workDao = KartovedDatabase.getDatabase(application).workDao()
        repository = WorkRepository(workDao)
        readAllData = repository.readAllData
    }

    fun getWork(cookie: String){
        viewModelScope.launch{
            val response = repository.getWork(cookie)
            workResponse.value = response
        }
    }

    fun addWorkInDatabase(work: Work){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWork(work)
        }
    }

    fun deleteAllWorkFromDatabase(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllWork()
        }
    }
}