package com.example.taskmanagement.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val userName: String,
    val userPassword: String,
    val userImg: Int

)
