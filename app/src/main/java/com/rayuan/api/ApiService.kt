package com.rayuan.api

import com.rayuan.response.ResponseRating
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("/prediction?key=AIzaSyCyZy2Y9hEmZfB0D9HN0sLU_giOPcJkDsQ")
    fun uploadImage(
        @Header("key") key : String,
        @Part("imagefile") imagefile: String
    ): Call<ResponseRating>

//    companion object{
//        const val api_key = "key:AIzaSyCyZy2Y9hEmZfB0D9HN0sLU_giOPcJkDsQ"
//    }
}