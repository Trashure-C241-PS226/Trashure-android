package com.example.trashure.data.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("data")
	val predictDetail: PredictDetail? = null,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class PredictDetail(

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("category")
	val category: Int? = null
)
