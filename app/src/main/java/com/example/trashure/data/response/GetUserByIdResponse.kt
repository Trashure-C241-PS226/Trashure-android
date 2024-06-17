package com.example.trashure.data.response

import com.google.gson.annotations.SerializedName

data class GetUserByIdResponse(

    @field:SerializedName("data")
    val data: DetailUser? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class DetailUser(

    @field:SerializedName("provinsi")
    val provinsi: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("kab_kota")
    val kabKota: String? = null,

    @field:SerializedName("kecamatan")
    val kecamatan: String? = null,

    @field:SerializedName("nomor")
    val nomor: String? = null,

    @field:SerializedName("items")
    val items: List<ItemsItem?>? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)

data class ItemsItem(

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

    @field:SerializedName("predict_harga")
    val predictHarga: Any? = null,

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
