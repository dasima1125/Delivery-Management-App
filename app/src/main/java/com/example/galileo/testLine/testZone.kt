package com.example.galileo.testLine

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class testZone {

    fun test01(routin :  Int){
        runBlocking {

            for (i in 1..routin) {
                launch{

                    testElmantal()
                }
            }
        }

    }
    suspend fun testElmantal(){


        Log.d("oiia", "testElmantal:start ")
        // 비동기 작업 시뮬레이션을 위해 1초 대기
        delay(2000)
        Log.d("oiia", "testElmantal:end ")


    }


}