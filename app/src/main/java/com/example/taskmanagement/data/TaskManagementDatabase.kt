package com.example.taskmanagement.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User

@Database(
    entities = [
        Task::class,
        SubTask::class,
        User::class,
        TaskMember::class
    ],
    version = 10
)
@TypeConverters(Converters::class)
abstract class TaskManagementDatabase : RoomDatabase() {

    abstract fun taskManagementDao(): TaskManagementDao

}