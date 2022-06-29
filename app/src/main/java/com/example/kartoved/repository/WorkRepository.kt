package com.example.kartoved.repository

import androidx.lifecycle.LiveData
import com.example.kartoved.data.dao.WorkDao
import com.example.kartoved.data.network.RetrofitInstance
import com.example.kartoved.model.Work
import retrofit2.Response

class WorkRepository(private val workDao: WorkDao) {

    val readAllData: LiveData<Work> = workDao.readAllData()

    suspend fun getWork(cookies: String): Response<ArrayList<Work>>{
        return RetrofitInstance.workApi.getWork(cookies)
    }

    suspend fun addWork(work: Work){
        workDao.addWork(work)
    }

    suspend fun deleteAllWork(){
        workDao.deleteAllWork()
    }
}