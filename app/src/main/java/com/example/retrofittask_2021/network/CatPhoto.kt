package com.example.retrofittask_2021.network

import com.squareup.moshi.Json

data class CatPhoto (
    val id: String,
    @Json(name="url") val imgSrcUrl: String
)
