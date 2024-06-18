package com.example.trashure.data.response

import com.google.gson.annotations.SerializedName

data class CreateItemResponse(

	@field:SerializedName("data")
	val items: Items? = null,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Items(

	@field:SerializedName("tahun_pemakaian")
	val tahunPemakaian: Int? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("minus")
	val minus: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("storage")
	val storage: String? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("collector_id")
	val collectorId: Int? = null,

	@field:SerializedName("model")
	val model: String? = null,

	@field:SerializedName("screen_size")
	val screenSize: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("battery_capacity")
	val batteryCapacity: String? = null,

	@field:SerializedName("camera")
	val camera: String? = null,

	@field:SerializedName("brand")
	val brand: String? = null,

	@field:SerializedName("ram")
	val ram: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
