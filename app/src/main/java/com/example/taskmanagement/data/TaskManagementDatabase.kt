package com.example.taskmanagement.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.User

@Database(
    entities = [
        Task::class,
        SubTask::class,
        User::class
    ],
    version = 4
)
abstract class TaskManagementDatabase : RoomDatabase() {

    abstract fun taskManagementDao(): TaskManagementDao

}