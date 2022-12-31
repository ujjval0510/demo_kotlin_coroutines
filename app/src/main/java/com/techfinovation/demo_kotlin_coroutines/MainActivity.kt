package com.techfinovation.demo_kotlin_coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";
    // NOTE : If main thread finish his work that means all other thread and coroutines will be cancelled.

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            /** once's the MainActivity is finished and redirect to second activity
             * but this coroutine with delay 1sec will still running in background even if this activity is destroyed,
             * and it causes for garbage collector or memory leak in application,
             * So what should we do ? **/
            /* GlobalScope.launch {
                     while (true) {
                         delay(1000L)
                         Log.d(TAG, "Still running...")
                     }
             }*/

            /** so for that we will use lifecycleScope
             * that will destroy or stop all coroutines onces activity destroy **/
            lifecycleScope.launch {
                while (true) {
                    delay(1000L)
                    Log.d(TAG, "Still running...")
                }
            }

            GlobalScope.launch {
                delay(5000L)
                Intent(this@MainActivity, SecondActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

}