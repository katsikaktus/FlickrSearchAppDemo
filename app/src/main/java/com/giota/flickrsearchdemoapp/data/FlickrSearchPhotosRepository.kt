package com.giota.flickrsearchdemoapp.data

import com.giota.flickrsearchdemoapp.network.FlickrSearchApiService
import com.giota.flickrsearchdemoapp.network.FlickrSearchResponse

interface FlickrSearchPhotosRepository {
    suspend fun getFlickrPhotos(tag: String): FlickrSearchResponse
}

class DefaultFlickrSearchPhotosRepository(
    private val flickrSearchApiService: FlickrSearchApiService,
    private val apiKey: String,
) : FlickrSearchPhotosRepository{
    override suspend fun getFlickrPhotos(tag: String): FlickrSearchResponse {
        return flickrSearchApiService.getPhotos(apiKey = apiKey, tags = tag)
    }

}