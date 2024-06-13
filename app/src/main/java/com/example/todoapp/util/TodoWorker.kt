package com.example.todoapp.util

import android.app.Activity
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TodoWorker(context: Context, params:WorkerParameters): Worker(context,params) {
    companion object {
        var activity: Activity ?= null
    }



    override fun doWork(): Result {
        NotificationHelper(applicationContext).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString())
        return Result.success()
    }


}

