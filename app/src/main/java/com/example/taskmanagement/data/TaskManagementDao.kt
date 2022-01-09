package com.example.taskmanagement.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskmanagement.data.entities.SubTask
import com.example.taskmanagement.data.entities.Task
import com.example.taskmanagement.data.entities.TaskMember
import com.example.taskmanagement.data.entities.User
import com.example.taskmanagement.data.entities.relations.TaskWithSubTasks
import com.example.taskmanagement.data.entities.relations.TaskWithTaskMembers

@Dao
interface TaskManagementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(subTask: SubTask)

    @Insert
    suspend fun insertTaskMember(taskMember: TaskMember)

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE Task SET task_progression =:task_progression  WHERE taskId =:taskId")
    suspend fun updateTask(task_progression: Float, taskId: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSubTaskStatus(subTask: SubTask)

    @Query("SELECT * FROM Task")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM TaskMember")
    fun getAllTaskMembers(): LiveData<List<TaskMember>>

    @Query("SELECT * FROM Task ORDER BY taskId DESC LIMIT 1")
    fun getNewestTask(): LiveData<Task>

    @Query("SELECT * FROM Task ORDER BY taskId DESC LIMIT 30 OFFSET 1")
    fun getAllTasksMinusNewestTask(): LiveData<List<Task>>


    @Transaction
    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    fun getTaskWithSubTasks(taskId: Int): LiveData<List<TaskWithSubTasks>>

    @Transaction
    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    fun getTaskWithTaskMembers(taskId: Int): LiveData<List<TaskWithTaskMembers>>

}