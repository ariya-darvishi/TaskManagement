package com.example.taskmanagement.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithUsers
import kotlinx.coroutines.flow.Flow

interface TaskManagementRepository {

    suspend fun insertTask(task: Task)

    suspend fun insertSubTask(subTask: SubTask)

    suspend fun insertUser(user: User)



    suspend fun getTaskWithSubTasks(taskTitle: String): Flow<List<TaskWithSubTasks>>

    suspend fun getTaskWithUsers(taskTitle: String): Flow<List<TaskWithUsers>>

}