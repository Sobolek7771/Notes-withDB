package com.example.kartoved.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kartoved.model.Work;

@Dao
interface WorkDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWork(work: Work)

    @Query("DELETE FROM work_table")
    suspend fun deleteAllWork()

    @Query("SELECT * FROM work_table")
    fun readAllData(): LiveData<Work>
}
