package com.example.testapp01.retrofit

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import com.example.testapp01.db.utils.Drink
import com.example.testapp01.db.utils.DrinkRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


public interface JsonPlaceholderApi{

//    companion object{
//        val remoteData = ArrayList<Drink>()
//    }

    @GET("posts")
    fun getPosts(): Call<List<Post>>


//this should be removed
    @GET("posts/{id}/comments")
    fun getComments(@Path("id") groupId: Int): Call<List<Comment>>


}