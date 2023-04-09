package com.giota.flickrsearchdemoapp.ui.screens

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
import com.giota.flickrsearchdemoapp.data.FlickrSearchPhotosRepository
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhotos
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface FlickrSearchUiState {
    data class Success(val photos: FlickrSearchPhotos) : FlickrSearchUiState
    object Error : FlickrSearchUiState
    object Loading : FlickrSearchUiState
}

class FlickrSearchViewModel(
    private val flickrSearchPhotosRepository: FlickrSearchPhotosRepository
) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var flickrSearchUiState: FlickrSearchUiState by mutableStateOf(FlickrSearchUiState.Loading)
        private set

    var userInput: String by mutableStateOf("")
        private set

    fun updateUserSearch(tag: String){
        userInput = tag
    }


    /**
     * Gets Flickr photos information from the Repository
     */
     fun getFlickrPhotos(tag: String) {
        viewModelScope.launch {
            flickrSearchUiState = try {
                val response = flickrSearchPhotosRepository.getFlickrPhotos(tag)
                FlickrSearchUiState.Success(response.photos)

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