package com.giota.flickrsearchdemoapp.network

import kotlinx.serialization.SerialName


data class FlickrSearchPhotos(
    val page: Int,
    val pages: Int,
    @SerialName(value = "perpage")
    val perPage: Int,
    val photo: List<FlickrSearchPhoto>,
    val total: Int
)