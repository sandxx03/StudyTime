package com.example.studytime.di

import com.example.studytime.data.repository.SessionRepositoryImpl
import com.example.studytime.data.repository.SubjectRepositoryImpl
import com.example.studytime.domain.model.Task
import com.example.studytime.domain.repository.SessionRepository
import com.example.studytime.domain.repository.SubjectRepository
import com.example.studytime.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    abstract fun bindSubjectRepository(
        impl: SubjectRepositoryImpl
    ): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: SubjectRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}