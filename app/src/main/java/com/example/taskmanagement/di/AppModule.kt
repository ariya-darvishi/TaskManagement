package com.example.taskmanagement.di

import android.content.Context
import androidx.room.Room
import com.example.taskmanagement.data.TaskManagementDatabase
import com.example.taskmanagement.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaskManagementDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        TaskManagementDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun provideTaskManagementDao(
        database: TaskManagementDatabase
    ) = database.taskManagementDao()


}