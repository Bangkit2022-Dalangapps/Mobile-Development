package com.example.dalangapp.retrofit.responses

import com.google.gson.annotations.SerializedName

data class ShopResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: ArrayList<ListShopItems>,

	@field:SerializedName("error")
	val error: String
)

data class ListShopItems(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("linkUrl")
	val linkUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
