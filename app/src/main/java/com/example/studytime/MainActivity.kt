package com.example.studytime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studytime.domain.model.Session
import com.example.studytime.domain.model.Subject
import com.example.studytime.domain.model.Task
import com.example.studytime.presentation.dashboard.DashboardScreen
import com.example.studytime.presentation.session.SessionScreen
import com.example.studytime.presentation.subject.SubjectScreen
import com.example.studytime.presentation.task.TaskScreen
import com.example.studytime.presentation.theme.StudyTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyTimeTheme {
                SessionScreen()


            }
        }
    }
}

// dummy subject list
val subjects = listOf(
    Subject(name = "English", goalHours = 10f, colors = Subject.subjectCardColors[0], subjectId = 0),
    Subject(name = "Physics", goalHours = 10f, colors = Subject.subjectCardColors[1],subjectId = 0),
    Subject(name = "Maths", goalHours = 10f, colors = Subject.subjectCardColors[2],subjectId = 0),
    Subject(name = "Geology", goalHours = 10f, colors = Subject.subjectCardColors[3],subjectId = 0),
    Subject(name = "Chinese", goalHours = 10f, colors = Subject.subjectCardColors[4],subjectId = 0),
)

// dummy task list
val tasks = listOf(
    Task(
        title = "Prepare notes",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = false,
        taskSubjectId = 0,
        taskId = 1
    ),

    Task(
        title = "Do Homework",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isComplete = true,
        taskSubjectId = 0,
        taskId = 1
    ),

    Task(
        title = "Go Swimming",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isComplete = false,
        taskSubjectId = 0,
        taskId = 1
    ),

    Task(
        title = "Assignment",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isComplete = false,
        taskSubjectId = 0,
        taskId = 1
    ),

    Task(
        title = "Write Poem",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = true,
        taskSubjectId = 0,
        taskId = 1
    )
)

// dummy session list
val sessions = listOf(
    Session(
        relatedToSubject = "English",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),

    Session(
        relatedToSubject = "Physics",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),

    Session(
        relatedToSubject = "Chemistry",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),

    Session(
        relatedToSubject = "Maths",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    )


)