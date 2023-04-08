package com.giota.flickrsearchdemoapp.data

import com.giota.flickrsearchdemoapp.network.FlickrSearchApi
import com.giota.flickrsearchdemoapp.network.FlickrSearchResponse
import com.giota.flickrsearchdemoapp.util.Constants

interface FlickrSearchPhotosRepository {
    suspend fun getFlickrPhotos(): FlickrSearchResponse
}

class DefaultFlickrSearchPhotosRepository : FlickrSearchPhotosRepository{
    override suspend fun getFlickrPhotos(): FlickrSearchResponse {
        return FlickrSearchApi.retrofitService.getPhotos(apiKey = Constants.API_KEY, tags = "cat")
    }

}