package com.example.taskmanagement.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubTask(

    @PrimaryKey(autoGenerate = true)
    val subTaskId: Int,
    val subTaskTitle: String,

    val taskId: Int

)
