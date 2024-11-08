package com.example.galileo.testLine

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.JobIntentService
import java.util.concurrent.TimeUnit

class PeriodicLogPrinterService : JobIntentService() {
    private val handler = Handler(Looper.getMainLooper())
    private var counter = 0

    private val periodicRunnable = object : Runnable {
        override fun run() {
            counter++
            Log.d("oiia3", "안녕$counter")

            Log.d("oiia3", " 테스트라인 ")

            // 다음 작업 예약
            handler.postDelayed(this, TimeUnit.MINUTES.toMillis(1))
        }
    }

    override fun onHandleWork(intent: Intent) {
        // 최초 실행
        handler.post(periodicRunnable)
    }

    companion object {
        private const val JOB_ID = 1000

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, PeriodicLogPrinterService::class.java, JOB_ID, work)
        }
    }
}
