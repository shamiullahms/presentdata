package com.app4free.presentdata.data

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val firm_id: Int,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val change: Double
)
