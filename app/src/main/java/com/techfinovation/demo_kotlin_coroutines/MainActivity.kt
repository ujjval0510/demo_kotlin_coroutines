package com.techfinovation.demo_kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**the below code will execute after 6 sec when both network
         *  call complete there process
         *  but that's the problem, How to resolve that ? **/
        funNetworkCall()

        /**We will create two different coroutines inside the global scope
         * then use join method for both jobs then it will take only 3 sec for execute both process
         * ===============
         * But this is not good practice for that what should we do ? **/
        funNetworkDifferentCoroutine()

        /** we can use async method to do that **/

        funAsync()
    }


    /** Always use async if we want to use coroutines that return some kind of results **/
    private fun funAsync() {
        GlobalScope.launch(Dispatchers.IO) {
            /**This measureTimeMillis will measure the exact time taken by the coroutine **/
            val time = measureTimeMillis {

                var answer1 = async { networkCall1() }
                var answer2 = async { networkCall2() }

                Log.d(TAG, "networkCall1 answer 1 :${answer1.await()} ")
                Log.d(TAG, "networkCall2 answer 2 :${answer2.await()} ")
            }

            /**this will also take only 3sec for execute both methods**/
            Log.d(TAG, "Request time :$time ms ")
        }
    }

    private fun funNetworkDifferentCoroutine() {
        GlobalScope.launch(Dispatchers.IO) {
            /**This measureTimeMillis will measure the exact time taken by the coroutine **/
            val time = measureTimeMillis {

                var answer1: String? = null
                var job1 = launch { answer1 = networkCall1() }

                var answer2: String? = null
                var job2 = launch { answer2 = networkCall2() }

                job1.join()
                job2.join()

                Log.d(TAG, "networkCall1 answer 1 :${answer1} ")

                Log.d(TAG, "networkCall2 answer 2 :${answer2} ")
            }

            /**this will take only 3sec for execute both methods**/
            Log.d(TAG, "Request time :$time ms ")
        }
    }

    private fun funNetworkCall() {
        GlobalScope.launch(Dispatchers.IO) {
            /**This measureTimeMillis will measure the exact time taken by the coroutine **/
            val time = measureTimeMillis {

                val answer1 = networkCall1()
                val answer2 = networkCall2()

                Log.d(TAG, "networkCall1 answer 1 :${answer1} ")
                Log.d(TAG, "networkCall2 answer 2 :${answer2} ")
            }
            /**this will take 6sec for execute both methods**/
            Log.d(TAG, "Request time :$time ms ")
        }
    }

    private suspend fun networkCall1(): String {
        delay(3000L)
        return "Network call 1"
    }

    private suspend fun networkCall2(): String {
        delay(3000L)
        return "Network call 2"
    }
}