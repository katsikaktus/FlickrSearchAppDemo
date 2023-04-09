package com.giota.flickrsearchdemoapp.network

import kotlinx.serialization.SerialName

data class FlickrSearchPhoto(
    val farm: Int,
    val id: String,
    @SerialName(value = "isfamily")
    val isFamily: Int,
    @SerialName(value = "isfriend")
    val isFriend: Int,
    @SerialName(value = "ispublic")
    val isPublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
)