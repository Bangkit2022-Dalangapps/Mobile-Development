package com.example.dalangapp.retrofit.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("error")
	val error: String
)
