package com.example.dalangapp.retrofit

import com.example.dalangapp.retrofit.responses.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("/register")
    fun register(
        @Part("name") name: String,
        @Part("email") email: String,
        @Part("password") password: String,
        @Part("confPassword") confPassword: String
    ): Call<RegisterResponse>

    @Multipart
    @POST("/login")
    fun login(
        @Part("email") email: String,
        @Part("password") password: String
    ): Call<LoginResponse>


    @GET("/Stories")
    fun getStories(

    ): Call<StoriesRespond>

    @GET("/Dalangs")
    fun getDalang(

    ): Call<DalangResponse>

    @GET("/Places")
    fun getMuseumStudio(

    ): Call<MuseumStudioResponse>

    @GET("/Shop")
    fun getShop(

    ): Call<ShopResponse>

    @GET("/Wayangs/{id}")
    fun getWayang(
        @Path("id") id: Int
    ): Call<WayangResponse>

}