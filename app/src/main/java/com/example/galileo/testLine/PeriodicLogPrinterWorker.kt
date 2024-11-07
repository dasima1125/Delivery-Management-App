package com.example.galileo.testLine

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.galileo.db.DBHandler
import java.util.concurrent.TimeUnit

class PeriodicLogPrinterWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        // 실제로 수행할 작업을 여기에 작성합니다.
        Log.d("testsync", "Reporting to logd...")
        val context = applicationContext
        val db = DBHandler(context)

        db.autoUpdater()

        // 작업이 성공적으로 완료되면 Result.success()를 반환합니다.
        return Result.success()
    }


}

object PeriodicLogPrinterWorkerHelper {

    const val WORKER_TAG = "PeriodicLogPrinterWorker01"

    fun startPeriodicLogPrinterWorker(context: Context) {

        val periodicWorkRequest = PeriodicWorkRequestBuilder<PeriodicLogPrinterWorker>(
            repeatInterval = 15 * 60 * 1000, // 주기 (밀리초 단위)
            TimeUnit.MILLISECONDS
        ).addTag(WORKER_TAG)

            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORKER_TAG,
            ExistingPeriodicWorkPolicy.KEEP, // 이미 등록된 작업이 있다면 교체 154355
            periodicWorkRequest
        )
    }
}