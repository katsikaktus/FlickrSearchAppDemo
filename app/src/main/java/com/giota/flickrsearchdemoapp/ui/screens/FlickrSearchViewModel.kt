package com.giota.flickrsearchdemoapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giota.flickrsearchdemoapp.network.FlickrSearchApi
import com.giota.flickrsearchdemoapp.util.Constants.API_KEY
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface FlickrSearchUiState {
    data class Success(val photos: String) : FlickrSearchUiState
    object Error : FlickrSearchUiState
    object Loading : FlickrSearchUiState
}

class FlickrSearchViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var flickrSearchUiState: FlickrSearchUiState by mutableStateOf(FlickrSearchUiState.Loading)
        private set

    /**
     * Call searchFlickrPhotos() on init so we can display status immediately.
     */
    init {
        searchFlickrPhotos()
    }

    /**
     * Gets Flickr photos information from the FlickrSearch API
     */
    private fun searchFlickrPhotos() {
        viewModelScope.launch {
            flickrSearchUiState = try {
                val listResult = FlickrSearchApi.retrofitService.searchPhotos(apiKey = API_KEY, tags = "cat")
                FlickrSearchUiState.Success(listResult.photos.photo[0].toString())
            } catch (e: IOException) {
                FlickrSearchUiState.Error
            }

        }

    }

}