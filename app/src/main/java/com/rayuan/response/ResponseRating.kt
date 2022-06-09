package com.rayuan.response

import com.google.gson.annotations.SerializedName

data class ResponseRating(

	@field:SerializedName("certainty")
	val certainty: String,

	@field:SerializedName("label")
	val label: String
)
