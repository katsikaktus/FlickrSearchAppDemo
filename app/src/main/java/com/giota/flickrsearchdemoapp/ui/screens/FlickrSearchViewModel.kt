package com.giota.flickrsearchdemoapp.ui.screens


import android.util.Log
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
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhoto
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhotos
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


enum class FlickrSearchError {
    NO_INTERNET_CONNECTION,
    NO_RESULTS_FOUND,
    UNKNOWN_ERROR
}

sealed interface FlickrSearchUiState {
    data class Success(val photos: FlickrSearchPhotos) : FlickrSearchUiState
    data class Error(val errorMessage: FlickrSearchError) : FlickrSearchUiState
    object Loading : FlickrSearchUiState
    object NoRequest : FlickrSearchUiState
}



class FlickrSearchViewModel(
    private val flickrSearchPhotosRepository: FlickrSearchPhotosRepository
) : ViewModel() {


    /** The mutable State that stores the status of the most recent request */
    var flickrSearchUiState: FlickrSearchUiState by mutableStateOf(FlickrSearchUiState.NoRequest)
        private set

    var userInput: String by mutableStateOf("")
        private set

    fun updateUserSearch(tag: String){
        userInput = tag
    }


    /** The selected photo */
    var selectedPhoto by mutableStateOf<FlickrSearchPhoto?>(null)
        private set

    /** Sets the selected photo */
    fun selectPhoto(photo: FlickrSearchPhoto) {
        selectedPhoto = photo
    }

    /** Clears the selected photo */
    fun clearSelectedPhoto() {
        selectedPhoto = null
    }




    /**
     * Gets Flickr photos information from the Repository
     */
     fun getFlickrPhotos(tag: String) {

        viewModelScope.launch {

            if (tag.isNotEmpty()){
                flickrSearchUiState = FlickrSearchUiState.Loading
                flickrSearchUiState = try {
                        val response = flickrSearchPhotosRepository.getFlickrPhotos(tag)
                        Log.d("res", response.toString())

                        if ( response.photos.photo.isNotEmpty()){
                            FlickrSearchUiState.Success(response.photos)
                        } else {
                            FlickrSearchUiState.Error(FlickrSearchError.NO_RESULTS_FOUND)
                        }

                    } catch (e: IOException) {
                        FlickrSearchUiState.Error(FlickrSearchError.NO_INTERNET_CONNECTION)

                    } catch (e: HttpException) {
                        FlickrSearchUiState.Error(FlickrSearchError.UNKNOWN_ERROR)
                    }
            } else {
                FlickrSearchUiState.NoRequest
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