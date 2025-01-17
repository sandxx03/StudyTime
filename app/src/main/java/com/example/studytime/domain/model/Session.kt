package com.example.studytime.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity // telling database that this Session class is an entity
data class Session(
    val sessionSubjectId: Int,
    val relatedToSubject: String,
    val date: Long,
    val duration: Long,

    @PrimaryKey(autoGenerate = true) //allocate SessionId as primary key (autogenerated)
    val sessionId: Int? = null
)
