package com.giota.flickrsearchdemoapp.data

import com.giota.flickrsearchdemoapp.network.FlickrSearchApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val flickrSearchPhotosRepository: FlickrSearchPhotosRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl= "https://www.flickr.com/"
    private val apiKey = "e0edf51c53287730e860de6f755f0315"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService : FlickrSearchApiService by lazy {
            retrofit.create(FlickrSearchApiService::class.java)
    }

    override val flickrSearchPhotosRepository: FlickrSearchPhotosRepository by lazy {
        DefaultFlickrSearchPhotosRepository(retrofitService, apiKey)
    }
}



