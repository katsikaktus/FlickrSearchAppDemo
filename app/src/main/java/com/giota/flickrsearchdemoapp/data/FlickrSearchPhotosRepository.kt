package com.giota.flickrsearchdemoapp.data

import com.giota.flickrsearchdemoapp.network.FlickrSearchApiService
import com.giota.flickrsearchdemoapp.network.FlickrSearchResponse
import com.giota.flickrsearchdemoapp.network.PhotoGetInfoResponse

interface FlickrSearchPhotosRepository {
    suspend fun getFlickrPhotos(tag: String): FlickrSearchResponse
    suspend fun getFlickrPhotoInfo(photoId: String, photoSecret: String): PhotoGetInfoResponse
}

class DefaultFlickrSearchPhotosRepository(
    private val flickrSearchApiService: FlickrSearchApiService,
    private val apiKey: String,
) : FlickrSearchPhotosRepository{
    override suspend fun getFlickrPhotos(tag: String): FlickrSearchResponse {
        return flickrSearchApiService.getPhotos(apiKey = apiKey, tags = tag)
    }

    override suspend fun getFlickrPhotoInfo(
        photoId: String,
        photoSecret: String
    ): PhotoGetInfoResponse {
        return flickrSearchApiService.getPhotoInfo(apiKey = apiKey, photoId = photoId, secret = photoSecret)
    }

}