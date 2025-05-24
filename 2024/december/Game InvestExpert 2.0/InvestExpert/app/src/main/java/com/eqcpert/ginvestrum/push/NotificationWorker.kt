package com.eqcpert.ginvestrum.push

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.eqcpert.ginvestrum.R

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val icon  = R.drawable.push_icon
        val title = inputData.getString("title") ?: "Default Title"
        val text  = inputData.getString("text")  ?: "Default Text"

        val push = Push(icon, title, text)

        NotificationManager.run {
            val notification = buildWithIntent(applicationContext, push)
            send(applicationContext, notification)
        }

        return Result.success()
    }
}