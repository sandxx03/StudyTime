package com.example.studytime.presentation.session

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.studytime.util.Constants.ACTION_SERVICE_CANCEL
import com.example.studytime.util.Constants.ACTION_SERVICE_START
import com.example.studytime.util.Constants.ACTION_SERVICE_STOP

class StudySessionTimerService: Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action.let{
            when(it){
                ACTION_SERVICE_START -> {

                }

                ACTION_SERVICE_STOP -> {

                }

                ACTION_SERVICE_CANCEL -> {

                }

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}