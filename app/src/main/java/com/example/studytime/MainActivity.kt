package com.example.studytime

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import com.example.studytime.domain.model.Session
import com.example.studytime.domain.model.Subject
import com.example.studytime.domain.model.Task
import com.example.studytime.presentation.NavGraph
import com.example.studytime.presentation.NavGraphs
import com.example.studytime.presentation.destinations.SessionScreenRouteDestination
import com.example.studytime.presentation.session.StudySessionTimerService
import com.example.studytime.presentation.theme.StudyTimeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.timer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isBound by mutableStateOf(false)
    private lateinit var timerService: StudySessionTimerService
    private val connection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val binder = service as StudySessionTimerService.StudySessionTimerBinder
            timerService = binder.getService()
            isBound = true

        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StudySessionTimerService::class.java).also{ intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (isBound){
                StudyTimeTheme {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        dependenciesContainerBuilder = {
                            dependency(SessionScreenRouteDestination){timerService}

                        }
                    )



                }

            }

        }
        requestPermission()
    }

    private fun requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}

// dummy subject list
val subjects = listOf(
    Subject(name = "English", goalHours = 10f, colors = Subject.subjectCardColors[0].map{it.toArgb()}, subjectId = 0),
    Subject(name = "Physics", goalHours = 10f, colors = Subject.subjectCardColors[1].map{it.toArgb()},subjectId = 0),
    Subject(name = "Maths", goalHours = 10f, colors = Subject.subjectCardColors[2].map{it.toArgb()},subjectId = 0),
    Subject(name = "Geology", goalHours = 10f, colors = Subject.subjectCardColors[3].map{it.toArgb()},subjectId = 0),
    Subject(name = "Chinese", goalHours = 10f, colors = Subject.subjectCardColors[4].map{it.toArgb()},subjectId = 0),
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