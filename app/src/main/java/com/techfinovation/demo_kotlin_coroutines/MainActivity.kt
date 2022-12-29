package com.techfinovation.demo_kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";

    /**  NOTE : If main thread finish his work that means all other thread and coroutines will be cancelled. **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** this coroutine will live as long as our application does.
        Dispatchers.Main : Main thread
        Dispatchers.IO : Data operation such as network call, writing to databases, read/ write files etc.
        Dispatchers.Unconfined means not confined with any thread.
        newSingleThreadContext("MyThread) : custom thread **/

        GlobalScope.launch(Dispatchers.Default) {
            val text = doNetworkCall();
            Log.d(TAG, "Starting coroutines in thread :${Thread.currentThread().name} ");

            withContext(Dispatchers.Main) {
                Log.d(TAG, "Setting text in thread :${Thread.currentThread().name} ");
                tv_dummy.text = text
            }
            delay(3000L)
            /** this will run different thread.**/
            Log.d(TAG, "Coroutines : hello from thread :${Thread.currentThread().name} ");
        }

        /** this will run on main thread **/
        Log.d(TAG, "Hello from main thread :${Thread.currentThread().name} ");

    }

    private suspend fun doNetworkCall(): String {
        delay(3000L)
        return "Network call function with 3sec delay ."
    }

}