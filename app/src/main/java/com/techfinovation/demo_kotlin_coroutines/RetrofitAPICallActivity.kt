package com.techfinovation.demo_kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects

class RetrofitAPICallActivity : AppCompatActivity() {
    private val BASE_URL: String = "https://jsonplaceholder.typicode.com/"
    private val TAG = "RetrofitAPICallActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_apicall)

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)

        /**enqueue function will start new thread for request.**/
        /*api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Log.d(TAG, "onFailure : $t")
            }

            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (comment in it) {
                            Log.d(TAG, comment.toString())
                        }
                    }
                }
            }
        })*/

        /** Using Coroutine **/
        GlobalScope.launch(Dispatchers.IO) {

            /** Option 1 : If we want result direct then we will use await() method like below **/
            /*val comments = api.getComments().await()
               for (comment in comments) {
                   Log.d(TAG, comment.toString())
               }*/
            /** Option 2 :  If we want response then we will use awaitResponse() method like below **/
            /*val response = api.getComments().awaitResponse();
            if (response.isSuccessful) {
                for (comment in response.body()!!) {
                    Log.d(TAG, comment.toString())
                }
            }*/
            /** Option 3 : If we want result direct then we will use await() method like below **/
            val response = api.getComments()
            Log.d(TAG, "" + response.body()!!.size)
            if (response.isSuccessful) {
                for (comment in response.body()!!) {
                    Log.d(TAG, comment.toString())
                }
            } else {
                Log.d(TAG, "Error ")
            }
        }

    }
}