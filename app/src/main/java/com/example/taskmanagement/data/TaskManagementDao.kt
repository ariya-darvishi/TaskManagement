package com.example.taskmanagement.data

import androidx.room.*
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskManagementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(subTask: SubTask)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM Task")
    suspend fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task ORDER BY taskId DESC LIMIT 1")
    suspend fun getNewestTask(): Flow<Task>

    @Query("SELECT * FROM Task ORDER BY taskId DESC LIMIT 30 OFFSET 1")
    suspend fun getAllTasksMinusNewestTask(): Flow<List<Task>>


    @Transaction
    @Query("SELECT * FROM SubTask WHERE taskId = :taskId")
    suspend fun getTaskWithSubTasks(taskId: String): Flow<List<TaskWithSubTasks>>

    @Transaction
    @Query("SELECT * FROM User WHERE taskId = :taskId")
    suspend fun getTaskWithUsers(taskId: String): Flow<List<TaskWithUsers>>

}