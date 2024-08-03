package com.example.studytime.presentation.task

import com.example.studytime.domain.model.Subject
import com.example.studytime.domain.model.Task
import com.example.studytime.util.Priority

sealed class TaskEvent {

    data class OnTitleChange(val title: String): TaskEvent()

    data class OnDescriptionChange(val description: String): TaskEvent()

    data class OnDateChange(val millis: Long?): TaskEvent()

    data class OnPriorityChange(val priority: Priority): TaskEvent()

    data class OnRelatedToSubjectSelect(val subject: Subject): TaskEvent()

    data object OnIsCompleteChange: TaskEvent()

    data object SaveTask: TaskEvent()

    data object DeleteTask: TaskEvent()
}