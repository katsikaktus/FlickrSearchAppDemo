package com.giota.flickrsearchdemoapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.giota.flickrsearchdemoapp.FlickrSearchPhotosApplication
import com.giota.flickrsearchdemoapp.data.DefaultFlickrSearchPhotosRepository
import com.giota.flickrsearchdemoapp.data.FlickrSearchPhotosRepository
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhoto
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface FlickrSearchUiState {
    data class Success(val photos: List<FlickrSearchPhoto>) : FlickrSearchUiState
    object Error : FlickrSearchUiState
    object Loading : FlickrSearchUiState
}

class FlickrSearchViewModel(
    private val flickrSearchPhotosRepository: FlickrSearchPhotosRepository
) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var flickrSearchUiState: FlickrSearchUiState by mutableStateOf(FlickrSearchUiState.Loading)
        private set

    /**
     * Call searchFlickrPhotos() on init so we can display status immediately.
     */
    init {
        getFlickrPhotos()
    }

    /**
     * Gets Flickr photos information from the Repository
     */
    private fun getFlickrPhotos() {
        viewModelScope.launch {
            flickrSearchUiState = try {
                val response = flickrSearchPhotosRepository.getFlickrPhotos()
                FlickrSearchUiState.Success(response.photos.photo)
            } catch (e: IOException) {
                FlickrSearchUiState.Error
            }

        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlickrSearchPhotosApplication)
                val flickrSearchPhotosRepository = application.container.flickrSearchPhotosRepository
                FlickrSearchViewModel(flickrSearchPhotosRepository = flickrSearchPhotosRepository)
            }
        }
    }

}