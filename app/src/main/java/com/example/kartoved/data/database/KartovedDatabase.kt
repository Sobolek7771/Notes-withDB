package com.example.kartoved.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kartoved.data.dao.NoteDao
import com.example.kartoved.data.dao.UserDao
import com.example.kartoved.data.dao.WorkDao
import com.example.kartoved.model.Note
import com.example.kartoved.model.User
import com.example.kartoved.model.Work

@Database (entities = [Note::class, User::class, Work::class], version = 2, exportSchema = false)
abstract class KartovedDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao
    abstract fun workDao(): WorkDao

    companion object{
        @Volatile
        private var INSTANCE: KartovedDatabase? =null

        fun getDatabase(context: Context): KartovedDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KartovedDatabase::class.java,
                    "kartoved_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}