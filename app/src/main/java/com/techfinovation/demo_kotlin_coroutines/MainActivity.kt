package com.techfinovation.demo_kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        runBlocking {
            delay(3000L)
        }

        /**  this will run on main thread **/
        Log.d(TAG, "Hello from main thread :${Thread.currentThread().name} ")

    }

}