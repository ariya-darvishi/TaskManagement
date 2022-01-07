package com.example.taskmanagement.data

import androidx.lifecycle.LiveData
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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSubTaskStatus(subTask: SubTask)

    @Query("SELECT * FROM Task")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM Task ORDER BY taskId DESC LIMIT 1")
    fun getNewestTask(): LiveData<Task>

    @Query("SELECT * FROM Task ORDER BY taskId DESC LIMIT 30 OFFSET 1")
    fun getAllTasksMinusNewestTask(): LiveData<List<Task>>


    @Transaction
    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    fun getTaskWithSubTasks(taskId: Int): LiveData<List<TaskWithSubTasks>>

    @Transaction
    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    fun getTaskWithUsers(taskId: Int): LiveData<List<TaskWithUsers>>

}