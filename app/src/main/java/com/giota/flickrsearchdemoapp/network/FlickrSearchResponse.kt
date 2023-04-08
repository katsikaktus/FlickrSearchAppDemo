package com.giota.flickrsearchdemoapp.network

data class FlickrSearchResponse(
    val photos: FlickrSearchPhotos,
    val stat: String
)