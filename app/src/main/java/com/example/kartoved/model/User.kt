package com.example.kartoved.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val username: String?,
    val first_name: String?,
    val second_name: String?,
    val phone: String?,
    val birthday: String?,
    val email: String?,
    val cookies: String?
    ): Parcelable