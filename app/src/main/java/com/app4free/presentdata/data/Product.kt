package com.app4free.presentdata.data

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("productName")
    @Json(name = "productName")
    val name: String,
    val imageFile: String,
    val description: String,
    val size: Int,
    val price: Double
)
