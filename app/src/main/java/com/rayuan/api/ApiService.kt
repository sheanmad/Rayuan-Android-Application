package com.rayuan.api

import com.rayuan.BuildConfig
import com.rayuan.response.ResponseRating
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/prediction")
    fun uploadImage(
        @Query("key") key : String,
        @Body imageFile: RequestBody
    ): Call<ResponseRating>

    companion object{
        const val api_key = BuildConfig.KEY
    }

}