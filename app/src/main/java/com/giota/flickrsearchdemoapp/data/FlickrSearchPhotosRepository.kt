package com.giota.flickrsearchdemoapp.data

import com.giota.flickrsearchdemoapp.network.FlickrSearchApiService
import com.giota.flickrsearchdemoapp.network.FlickrSearchResponse
import com.giota.flickrsearchdemoapp.network.PhotoGetInfoResponse

/** This interface defines the methods for accessing Flickr photo search and information APIs*/
interface FlickrSearchPhotosRepository {
    suspend fun getFlickrPhotos(tag: String): FlickrSearchResponse
    suspend fun getFlickrPhotoInfo(photoId: String, photoSecret: String): PhotoGetInfoResponse
}

/** This class implements the FlickrSearchPhotosRepository interface
 * using the FlickrSearchApiService to make API requests*/
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