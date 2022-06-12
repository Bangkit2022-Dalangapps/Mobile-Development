package com.example.dalangapp.retrofit.responses

import com.google.gson.annotations.SerializedName

data class StoriesRespond(

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("data")
    val data: ArrayList<ListStoryItems>,

    @field:SerializedName("error")
    val error: String
)

data class ListStoryItems(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)
