package com.example.retrofittask_2021.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.thecatapi.com/v1/"
private const val API_KEY = "419277a5-2dbe-438a-a5f8-1b4e2a448e8d"
private val interceptor = HttpLoggingInterceptor()
val client = OkHttpClient.Builder()
    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC))
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .build()

interface CatApiService {
    @Headers(
        "limit: 10",
        "x-api-key: $API_KEY"
    )

    @GET("images/search")
    suspend fun getPhotos(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("order") order: String = "asc",
        @Query("size") size: String = "med"
    ): List<CatPhoto>
}

object CatApi {
    val retrofitService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }
}
