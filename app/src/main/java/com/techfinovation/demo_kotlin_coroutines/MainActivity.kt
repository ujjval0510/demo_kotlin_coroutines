package com.techfinovation.demo_kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";
    // NOTE : If main thread finish his work that means all other thread and coroutines will be cancelled.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // this coroutine will live as long as our application does.
        GlobalScope.launch {
            // Suspend thread : it will delay the current coroutine to 3sec
            // Sleep thread : it will delay or suspend all the coroutine inside the thread.
            delay(3000L)
            // this will run different thread.
            Log.d(TAG, "Coroutines : hello from thread :${Thread.currentThread().name} ");
        }
        // this will run on main thread
        Log.d(TAG, "Hello from main thread :${Thread.currentThread().name} ");
    }

}