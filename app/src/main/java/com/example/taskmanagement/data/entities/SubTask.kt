package com.example.taskmanagement.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubTask(

    @PrimaryKey(autoGenerate = false)
    val subTaskTitle: String,

    val taskTitle: String

)
