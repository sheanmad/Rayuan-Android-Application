package com.rayuan.api

import com.rayuan.response.ResponseRating
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/prediction")
    fun uploadImage(
        @Query("key") key : String,
        @Body imagefile: RequestBody
    ): Call<ResponseRating>

    companion object{
        const val api_key = "AIzaSyCyZy2Y9hEmZfB0D9HN0sLU_giOPcJkDsQ"
    }

}