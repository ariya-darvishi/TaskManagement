package com.example.taskmanagement.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(

    @PrimaryKey(autoGenerate = true)
    val taskId: Int,
    val taskTitle: String,
    val shortDescription: String,
    val longDescription: String,
    val taskImg: String

)
