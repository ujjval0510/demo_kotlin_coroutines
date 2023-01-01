package com.techfinovation.demo_kotlin_coroutines;

import retrofit2.Call;
import retrofit2.Response
import retrofit2.http.GET;
import java.util.List;

interface MyAPI {
    @GET("/comments")
    /** Option 1 : Just add method without suspend function like below **/
//    fun getComments(): Call<List<Comments>>
    /** Option 2 : Just add method with suspend function like below **/
    suspend fun getComments(): Response<List<Comments>>
}
