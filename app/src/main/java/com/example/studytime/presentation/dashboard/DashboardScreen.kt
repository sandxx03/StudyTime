package com.example.studytime.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studytime.R
import com.example.studytime.domain.model.Session
import com.example.studytime.domain.model.Subject
import com.example.studytime.domain.model.Task
import com.example.studytime.presentation.components.AddSubjectDialog
import com.example.studytime.presentation.components.CountCard
import com.example.studytime.presentation.components.DeleteDialog
import com.example.studytime.presentation.components.SubjectCard
import com.example.studytime.presentation.components.studySessionList
import com.example.studytime.presentation.components.tasksList

@Composable
fun DashboardScreen(){

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

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) } // save state when rotate screen or change to dark/light mode
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) } // save state when rotate screen or change to dark/light mode
    var subjectName by remember { mutableStateOf("")}
    var goalHours by remember{ mutableStateOf("")}
    var selectedColor by remember { mutableStateOf(Subject.subjectCardColors.random())}

    // add subject
    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        subjectName = subjectName,
        goalHours = goalHours,
        onSubjectNameChange = {subjectName = it },
        onGoalHoursChange = {goalHours = it},
        selectedColors = selectedColor,
        onColorChange = {selectedColor = it},
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = {
            isAddSubjectDialogOpen = false
        }
    )

    // delete subject
    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session?",
        bodyText = "Are you sure you want to delete this session? Your studied hours will be reduced " +
                "by this session time. This action can not be undone.",
        onDismissRequest = { isDeleteSessionDialogOpen = false},
        onConfirmButtonClick = { isDeleteSessionDialogOpen = false}
    )


    // place elements like top Head bar, bottom head bar, floating action button at the right place
    Scaffold (
        topBar = { DashboardScreenTopBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Count Card Section
            item{
                CountCardSection(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    subjectCount = 5,
                    studiedHours = "10",
                    goalHours = "15"
                )
            }

            //Subject Card Section
            item{
                SubjectCardSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjects,
                    onAddIconClicked = {
                        isAddSubjectDialogOpen = true
                    }
                )

            }
            item{
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ){
                    Text(text = "Start Study Session")
                }
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "No upcoming tasks.\n" + "Click '+' to add new task.",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }

            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "No recent study sessions.\n",
                sessions = sessions,
                onDeleteIconClick = {isDeleteSessionDialogOpen = true}

            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "StudyTime",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}

@Composable
private fun CountCardSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
){

    Row (modifier = modifier){
        CountCard(
            modifier = Modifier.weight(1f), // equal size for all 3 cards
            headingText = "Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))

        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))

        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )



    }

}

@Composable
private fun SubjectCardSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "No subjects available. \n Click '+' to add new subject.",
    onAddIconClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )

            IconButton(onClick = onAddIconClicked) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Subject"
                )

            }

        }
        // check if the subject list is empty
        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .alpha(0.3f),
                painter = painterResource(R.drawable.img_book),
                contentDescription = emptyListText
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(1f),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors,
                    onClick = {}

                )



            }
        }
    }
}
