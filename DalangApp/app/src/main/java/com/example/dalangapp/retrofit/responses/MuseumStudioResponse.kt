package com.example.dalangapp.retrofit.responses

import com.google.gson.annotations.SerializedName

data class MuseumStudioResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: ArrayList<ListMuseumStudioItems>,

	@field:SerializedName("error")
	val error: String
)

data class ListMuseumStudioItems(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
