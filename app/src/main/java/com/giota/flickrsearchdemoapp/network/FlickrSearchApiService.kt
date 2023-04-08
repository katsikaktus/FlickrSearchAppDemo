package com.giota.flickrsearchdemoapp.network

import com.giota.flickrsearchdemoapp.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object FlickrSearchApi {
    val retrofitService : FlickrSearchApiService by lazy {
        retrofit.create(FlickrSearchApiService::class.java)
    }
}

interface FlickrSearchApiService {
    @GET("services/rest/")
    suspend fun getPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String,
        @Query("tags") tags: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): FlickrSearchResponse
}