package com.example.kartoved.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteName: String,
    val noteText: String,
    val coordinates: String
    ): Parcelable