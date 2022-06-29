package com.example.kartoved.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kartoved.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table")
    fun readAllData(): LiveData<User>
}