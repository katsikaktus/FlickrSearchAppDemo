package com.giota.flickrsearchdemoapp.data

import com.giota.flickrsearchdemoapp.network.FlickrSearchApiService
import com.giota.flickrsearchdemoapp.network.FlickrSearchResponse
import com.giota.flickrsearchdemoapp.util.Constants

interface FlickrSearchPhotosRepository {
    suspend fun getFlickrPhotos(): FlickrSearchResponse
}

class DefaultFlickrSearchPhotosRepository(
    private val flickrSearchApiService: FlickrSearchApiService,
    private val apiKey: String
) : FlickrSearchPhotosRepository{
    override suspend fun getFlickrPhotos(): FlickrSearchResponse {
        return flickrSearchApiService.getPhotos(apiKey = apiKey, tags = "cat")
    }

}