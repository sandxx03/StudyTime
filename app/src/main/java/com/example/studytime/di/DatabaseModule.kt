package com.example.studytime.di

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import com.example.studytime.data.local.AppDatabase
import com.example.studytime.data.local.MIGRATION_1_2
import com.example.studytime.data.local.SessionDao
import com.example.studytime.data.local.SubjectDao
import com.example.studytime.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides //responsible for providing dependencies for objects
    @Singleton
    fun provideDatabase(
        application: Application
    ): AppDatabase{
        return Room
            .databaseBuilder(
            application,
            AppDatabase::class.java,
            "studytime.db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideSubjectDao(database: AppDatabase): SubjectDao{
        return database.subjectDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideSessionDao(database: AppDatabase): SessionDao {
        return database.sessionDao()
    }
}