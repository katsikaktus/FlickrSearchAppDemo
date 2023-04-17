package com.giota.flickrsearchdemoapp.network


import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrSearchApiService {
    @GET("services/rest/")
    suspend fun getPhotos(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String,
        @Query("tags") tags: String,
        @Query("sort") sort: String = "relevance",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): FlickrSearchResponse


    @GET("services/rest/")
    suspend fun getPhotoInfo(
        @Query("method") method: String = "flickr.photos.getInfo",
        @Query("api_key") apiKey: String,
        @Query("photo_id") photoId: String,
        @Query("secret") secret: String = "relevance",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1
    ): PhotoGetInfoResponse
}