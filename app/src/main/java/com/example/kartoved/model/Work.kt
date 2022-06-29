package com.example.kartoved.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "work_table")
data class Work(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String?,
    val task: String?,
    val active: Boolean?,
    val type_work: String?,
    val executor_id: Int?,
    val group_coordinates: String?,
    val done: Boolean?
    ):Parcelable