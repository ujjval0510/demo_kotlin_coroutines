package com.techfinovation.demo_kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";
    // NOTE : If main thread finish his work that means all other thread and coroutines will be cancelled.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coroutineJoin()
        coroutineCancel()
        coroutineCancelWithTimeout()
    }

    private fun coroutineJoin() {
        Log.d(TAG, "coroutineJobWaitCancel : Start")
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                Log.d(TAG, "coroutineJoin : Coroutine is still working")
                delay(1000L)
            }
        }
        /** job.join()  : we need to call join function, but we can not call
         * outside to suspend or coroutine function
         * that's why we will use runBlocking for that **/
        runBlocking {
            // we wait for completion of the coroutine using the user of join function
            // then we went for main thread and print
            job.join()
            Log.d(TAG, "coroutineJoin : main thread is working")
        }
    }

    private fun coroutineCancel() {
        Log.d(TAG, "coroutineJobWaitCancel : Start")
        val job = GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG, "coroutineCancel : Start long running calculation")

            for (i in 30..40) {
                /** We need to check our coroutine is cancelled or still active otherwise
                 * even if we cancelled our job in runBlocking function then also it will continue because coroutine is busy
                 * with long-running calculation, we must have to check if it's active then we need to do our calculation otherwise cancel it.  **/
                if (isActive) {
                    Log.d(TAG, "coroutineCancel : Result for i = $i : ${fib(i)}")
                }
            }
            Log.d(TAG, "coroutineCancel : Ending long running calculation")
        }
        /** job.cancel() : we need to call join function, but we can not call
         * outside to suspend or coroutine function
         * that's why we will use runBlocking for that **/
        runBlocking {
            /** Delay our main thread with 2 sec **/
            delay(2000L)
            /** then we will cancel our coroutine and start main thread and print**/
            job.cancel()
            Log.d(TAG, "coroutineCancel : main thread is working")
        }
    }


    private fun coroutineCancelWithTimeout() {
        Log.d(TAG, "coroutineJobWaitCancel : Start")

        /** If we don't want to use runBlocking method to cancel our coroutines then
         * we have withTimeout method, but it will cancel the coroutine in some specific time **/

        GlobalScope.launch(Dispatchers.Default) {

            Log.d(TAG, "coroutineCancel : Start long running calculation")


            withTimeout(3000L) {
                /** So this function automatically cancel the coroutine after 3sec**/

                for (i in 30..40) {
                    /** We need to check our coroutine is cancelled or still active otherwise
                     * even if we cancelled our job in runBlocking function then also it will continue because coroutine is busy
                     * with long-running calculation, we must have to check if it's active then we need to do our calculation otherwise cancel it.  **/
                    if (isActive) {
                        Log.d(TAG, "coroutineCancel : Result for i = $i : ${fib(i)}")
                    }
                }
            }

            Log.d(TAG, "coroutineCancel : Ending long running calculation")
        }
    }

    /** use for long-running calculation to see actually how job cancel
     * will work while long-running coroutine is in progress **/
    fun fib(n: Int): Long {
        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n - 1) + fib(n - 2)
    }
}