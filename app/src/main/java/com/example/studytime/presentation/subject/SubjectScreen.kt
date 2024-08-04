package com.example.studytime.presentation.subject


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.studytime.presentation.components.AddSubjectDialog
import com.example.studytime.presentation.components.CountCard
import com.example.studytime.presentation.components.DeleteDialog
import com.example.studytime.presentation.components.SubjectImage
import com.example.studytime.presentation.components.studySessionList
import com.example.studytime.presentation.components.tasksList
import com.example.studytime.presentation.destinations.TaskScreenRouteDestination
import com.example.studytime.presentation.task.TaskScreenNavArgs
import com.example.studytime.util.SnackbarEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

// for navigation
data class SubjectScreenNavArgs(
    val subjectId: Int
)

@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun SubjectScreenRoute(
    navigator: DestinationsNavigator
){
    val viewModel: SubjectViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SubjectScreen(
        state = state,
        onEvent = viewModel::onEvent,
        snackbarEvent = viewModel.snackbarEventFlow,
        onBackButtonClick = {navigator.navigateUp()},
        onAddTaskButtonClick = {
            val navArg = TaskScreenNavArgs(taskId = null, subjectId = state.currentSubjectId)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        },

        onTaskCardClick = { taskId ->
            val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArg))
        }
            )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    state: SubjectState,
    onEvent: (SubjectEvent) -> Unit,
    snackbarEvent: SharedFlow<SnackbarEvent>,
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick:() -> Unit,
    onTaskCardClick: (Int?) -> Unit

){

    val listState = rememberLazyListState()
    val isFABExpanded by remember{ derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) } // save state when rotate screen or change to dark/light mode
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) } // save state when rotate screen or change to dark/light mode
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    val snackBarHostState = remember{ SnackbarHostState() }

    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // Create an ActivityResultLauncher to pick an image
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest{ event ->
            when(event){
                is SnackbarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackbarEvent.NavigateUp -> {
                    onBackButtonClick()
                }
            }
        }

    }
    
    LaunchedEffect(key1 = state.studiedHours, key2 = state.goalStudyHours) {
        onEvent(SubjectEvent.UpdateProgress)
        
    }

    // add subject
    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        subjectName = state.subjectName,
        goalHours = state.goalStudyHours,
        onSubjectNameChange = {onEvent(SubjectEvent.OnSubjectNameChange(it))},
        onGoalHoursChange = {onEvent(SubjectEvent.OnGoalStudyHoursChange(it))},
        selectedColors = state.subjectCardColors,
        onColorChange = {onEvent(SubjectEvent.OnSubjectCardColorChange(it))},
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(SubjectEvent.UpdateSubject)
            isAddSubjectDialogOpen = false
        }
    )

    // delete subject
    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Subject?",
        bodyText = "Are you sure you want to delete this subject? All related " +
                "tasks and study sessions will be permanently removed. This action can not be undone.",
        onDismissRequest = { isDeleteSubjectDialogOpen = false},
        onConfirmButtonClick = {
            onEvent(SubjectEvent.DeleteSubject)
            isDeleteSubjectDialogOpen = false


        }
    )

    // delete session
    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session?",
        bodyText = "Are you sure you want to delete this session? Your studied hours will be reduced " +
                "by this session time. This action can not be undone.",
        onDismissRequest = { isDeleteSessionDialogOpen = false},
        onConfirmButtonClick = {
            onEvent(SubjectEvent.DeleteSession)
            isDeleteSessionDialogOpen = false}
    )


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)},
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopBar(
                title = state.subjectName,
                onBackButtonClick = onBackButtonClick,
                onDeleteButtonClick = {isDeleteSubjectDialogOpen = true},
                onEditButtonClick = {isAddSubjectDialogOpen = true},
                scrollBehavior = scrollBehavior
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTaskButtonClick,
                icon = {Icon(imageVector = Icons.Default.Add, contentDescription = "Add")},
                text = {Text(text = "Add Task")},
                expanded = isFABExpanded
            )
        }
    ){ paddingValue ->
        LazyColumn (
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {

            item {
                SubjectImage(
                    imageUri = selectedImageUri ?: state.imageUri?.let { Uri.parse(it) },
                    onImageUriChange = {
                        selectedImageUri = it
                        onEvent(SubjectEvent.OnImageUriChange(it.toString()))
                    }
                )
            }


            item{
                SubjectOverviewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    studiedHours = state.studiedHours.toString(),
                    goalHours = state.goalStudyHours,
                    progress = state.progress
                )
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "No upcoming tasks.\n" + "Click '+' to add new task.",
                tasks = state.upcomingTasks,
                onCheckBoxClick = {onEvent(SubjectEvent.OnTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }

            tasksList(
                sectionTitle = "COMPLETED TASKS",
                emptyListText = "You have not completed any task.\n" + "Click the check box after completing a task.",
                tasks = state.completedTasks,
                onCheckBoxClick = {onEvent(SubjectEvent.OnTaskIsCompleteChange(it))},
                onTaskCardClick = onTaskCardClick
            )

            item{
                Spacer(modifier = Modifier.height(20.dp))
            }

            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "No recent study sessions.\n",
                sessions = state.recentSessions,
                onDeleteIconClick = {
                    onEvent(SubjectEvent.OnDeleteSessionButtonClick(it))
                    isDeleteSessionDialogOpen = true}

            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreenTopBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior

){
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon ={
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "navigate back"
                )

            }
        },
        title = { Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineSmall
        )},
        actions = {
            IconButton(onClick =  onDeleteButtonClick){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )
            }

            IconButton(onClick = onEditButtonClick){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )
            }
        }

    )
}

@Composable
private fun SubjectOverviewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress: Float
){
    val percentageProgress = remember(progress){
        (progress * 100).toInt().coerceIn(0,100)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically

    ){
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))

        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )

            Text(text = "$percentageProgress%")
        }


    }
}

