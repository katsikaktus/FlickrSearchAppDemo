package com.giota.flickrsearchdemoapp.data

import com.giota.flickrsearchdemoapp.network.FlickrSearchApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Contract for providing dependencies to the application. Here is a FlickrSearchPhotosRepository*/
interface AppContainer {
    val flickrSearchPhotosRepository: FlickrSearchPhotosRepository
}

/** Implementation of the dependencies*/
class DefaultAppContainer : AppContainer {
    private val baseUrl= "https://www.flickr.com/"
    private val apiKey = "e0edf51c53287730e860de6f755f0315"

    /** Create a Retrofit instance with the base URL and Gson converter factory*/
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /** The retrofitService property is initialized lazily, and creates a Retrofit service instance
    hat will be used to make API calls to the Flickr API.*/
    private val retrofitService : FlickrSearchApiService by lazy {
            retrofit.create(FlickrSearchApiService::class.java)
    }

    /** The flickrSearchPhotosRepository property is also initialized lazily, and creates a
     * DefaultFlickrSearchPhotosRepository instance using the retrofitService and apiKey.
     * This repository implementation will be used to fetch photo search and photo info data
     * from the Flickr API.*/
    override val flickrSearchPhotosRepository: FlickrSearchPhotosRepository by lazy {
        DefaultFlickrSearchPhotosRepository(retrofitService, apiKey)
    }
}



