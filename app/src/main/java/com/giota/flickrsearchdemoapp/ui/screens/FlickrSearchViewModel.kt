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
import com.giota.flickrsearchdemoapp.network.Photo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/** Define an enum class for possible error types during api calls */
enum class FlickrSearchError {
    NO_INTERNET_CONNECTION,
    NO_RESULTS_FOUND,
    UNKNOWN_ERROR
}

/** Define an interface for the UI state for the flickr search*/
sealed interface FlickrSearchUiState {
    data class Success(val photos: FlickrSearchPhotos) : FlickrSearchUiState
    data class Error(val errorMessage: FlickrSearchError) : FlickrSearchUiState
    object Loading : FlickrSearchUiState
    object NoRequest : FlickrSearchUiState
}

/**  Define an interface for the UI state of photo information */
sealed interface PhotoInfoUiState {
    data class Success(val photo: Photo) : PhotoInfoUiState
    data class Error(val errorMessage: FlickrSearchError) : PhotoInfoUiState
    object NoRequest : PhotoInfoUiState
}


/**  Define the ViewModel class of the app */
class FlickrSearchViewModel(
    private val flickrSearchPhotosRepository: FlickrSearchPhotosRepository
) : ViewModel() {

    /** The mutable State that stores the status of the most recent search request */
    var flickrSearchUiState: FlickrSearchUiState by mutableStateOf(FlickrSearchUiState.NoRequest)
        private set

    /** The mutable State that stores the status of the most recent get photo info request */
    var photoInfoUiState: PhotoInfoUiState by mutableStateOf(PhotoInfoUiState.NoRequest)
        private set

    /** The mutable State that stores the status of the user input for the search bar */
    var userInput: String by mutableStateOf("")
        private set


    /** The mutable State that stores the status of the most recent search request */
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
        photoInfoUiState = PhotoInfoUiState.NoRequest
    }

    /** Gets Flickr photos from the Repository*/
     fun getFlickrPhotos(tag: String) {

        viewModelScope.launch {

            if (tag.isNotEmpty()){
                flickrSearchUiState = FlickrSearchUiState.Loading
                flickrSearchUiState = try {
                        val response = flickrSearchPhotosRepository.getFlickrPhotos(tag)

                         // Check if there are search results
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

    /** Gets information for a specific photo from the Repository*/
    fun getPhotoInfo(photoId: String, photoSecret: String){
        viewModelScope.launch {
            photoInfoUiState = try {
                val response = flickrSearchPhotosRepository.getFlickrPhotoInfo(photoId, photoSecret)

                PhotoInfoUiState.Success(response.photo)

            } catch (e: IOException) {
                PhotoInfoUiState.Error(FlickrSearchError.NO_INTERNET_CONNECTION)

            } catch (e: HttpException) {
                PhotoInfoUiState.Error(FlickrSearchError.UNKNOWN_ERROR)
            }


        }

    }
    /** This is a companion object that contains a Factory property that creates an instance of the
     * FlickrSearchViewModel with the FlickrSearchPhotosRepository dependency injected.*/

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