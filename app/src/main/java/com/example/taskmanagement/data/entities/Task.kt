package com.example.taskmanagement.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    @PrimaryKey(autoGenerate = false)
    val taskTitle: String,
    val shortDescription: String,
    val longDescription: String

)
