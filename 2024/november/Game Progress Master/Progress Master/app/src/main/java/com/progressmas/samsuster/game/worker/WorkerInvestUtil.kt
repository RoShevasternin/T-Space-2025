package com.progressmas.samsuster.game.worker

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.progressmas.samsuster.appContext
import com.progressmas.samsuster.util.log
import java.util.concurrent.TimeUnit

class WorkerInvestUtil {

    fun startWorkerInvest(screenTypeIndex: Int, investmentIndex: Int, investment: Int, duration: Long) {
        val inputData = Data.Builder()
            .putInt("screenTypeIndex", screenTypeIndex)
            .putInt("investmentIndex", investmentIndex)
            .putInt("investment", investment)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<WorkerInvest>()
            .setInitialDelay(duration, TimeUnit.SECONDS)
            .addTag("invest")
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(appContext).enqueue(workRequest)
    }

    suspend fun collectWorkerInvest(block: (Int, Int, Int) -> Unit) {
        WorkManager.getInstance(appContext).getWorkInfosByTagFlow("invest").collect {
            it.onEach { workInfo ->

                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val screenTypeIndex = workInfo.outputData.getInt("screenTypeIndex", 0)
                    val investmentIndex = workInfo.outputData.getInt("investmentIndex", 0)
                    val investment      = workInfo.outputData.getInt("investment", 0)

                    block(screenTypeIndex, investmentIndex, investment)
                    log("Результат роботи: $screenTypeIndex | $investmentIndex | $investment")

                    WorkManager.getInstance(appContext).pruneWork()
                }
            }
        }
    }

}