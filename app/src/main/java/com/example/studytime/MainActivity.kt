package com.example.studytime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studytime.presentation.dashboard.DashboardScreen
import com.example.studytime.presentation.theme.StudyTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudyTimeTheme {
                DashboardScreen()


            }
        }
    }
}