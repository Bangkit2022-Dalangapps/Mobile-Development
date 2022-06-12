package com.example.dalangapp.retrofit.responses

import com.google.gson.annotations.SerializedName

data class WayangResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: WayangData,

	@field:SerializedName("error")
	val error: String
)

data class WayangData(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("origin")
	val origin: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("source")
	val source: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
