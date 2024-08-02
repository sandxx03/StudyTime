package com.example.studytime.presentation.dashboard

import androidx.lifecycle.ViewModel
import com.example.studytime.domain.repository.SubjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel

// observes changes in repository and provide changes to ui
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
): ViewModel(){


}