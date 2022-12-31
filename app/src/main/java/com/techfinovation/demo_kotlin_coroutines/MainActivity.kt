package com.techfinovation.demo_kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

/** runBlocking **/
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";

    /** NOTE : If main thread finish his work that means all other thread and coroutines will be cancelled. **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * We can not use delay function outside the Global scope / suspend function
         * that will actually create a new thread not run in main thread
         * that will also not be blocking our UI.
         */
        // delay(3000L) Not use gives compile error
        GlobalScope.launch {
            delay(3000L)
            /** this will run different thread.**/
            Log.d(TAG, "Coroutines : hello from thread :${Thread.currentThread().name} ")
        }

        /**
         * So what we should do if we want to run delay function without using coroutines or
         * suspend function / Global scope that time you can use runBlocking function.
         */
        /**
         * runBlocking actually block the main thread
         * it will block our UI updates.
         * this will use when you don't need coroutines behaviour
         * but still want to use suspend function in your main thread.
         * **/

        Log.d(TAG, "Before runBlocking ....")
        runBlocking {

            /** we can also launch our coroutine inside the runBlocking method **/
            /**Both coroutines execute on the same time as we have delay for 3sec in below example.**/
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO Coroutine")
            }

            launch {
                delay(3000L)
                Log.d(TAG, "Finished IO Coroutine 2 ")
            }

            Log.d(TAG, "Start runBlocking ....")

            delay(5000L)

            Log.d(TAG, "End runBlocking ....")
        }
        Log.d(TAG, "After runBlocking ....")
    }

}