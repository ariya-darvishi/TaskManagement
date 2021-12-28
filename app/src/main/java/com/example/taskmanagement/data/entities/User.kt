package com.example.taskmanagement.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = false)
    val userName: String,
    val userPassword: String,

    val taskTitle: String

)
