package com.gpecspertisa.sollomka.game.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerInvest(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val screenTypeIndex = inputData.getInt("screenTypeIndex", 0)
        val investmentIndex = inputData.getInt("investmentIndex", 0)
        val investment      = inputData.getInt("investment", 0)

        val outputData = Data.Builder()
            .putInt("screenTypeIndex", screenTypeIndex)
            .putInt("investmentIndex", investmentIndex)
            .putInt("investment", investment)
            .build()

        return Result.success(outputData)
    }
}