package com.example.retrofittask_2021.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.thecatapi.com/v1/"
private const val API_KEY = "419277a5-2dbe-438a-a5f8-1b4e2a448e8d"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CatApiService {
    @GET("images/search")
    fun getPhotos(): String
}

object CatApi {
    val retrofitService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}