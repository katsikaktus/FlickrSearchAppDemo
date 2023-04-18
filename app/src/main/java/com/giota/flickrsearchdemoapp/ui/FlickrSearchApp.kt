package com.giota.flickrsearchdemoapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giota.flickrsearchdemoapp.ui.screens.*
import com.giota.flickrsearchdemoapp.util.formatUnixTimestamp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FlickrSearchApp(
    modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {

            /** Obtain an instance of FlickrSearchViewModel */
            val flickrViewModel: FlickrSearchViewModel =
                viewModel(factory = FlickrSearchViewModel.Factory)

            /** The selected photo property is used for the navigation in the app. If there is
             * a photo selected then the app displays the details of that photo. Otherwise, the app
             * displays the SearchScreen*/
            val selectedPhoto = flickrViewModel.selectedPhoto

            if (selectedPhoto != null) {

                /** The arguments passed to the details screen depend on the status of the getInfo
                 * API call*/
                when(flickrViewModel.photoInfoUiState){
                    is PhotoInfoUiState.NoRequest -> {
                        flickrViewModel.getPhotoInfo(
                            photoId = selectedPhoto.id,
                            photoSecret = selectedPhoto.secret
                        )
                    }
                    is PhotoInfoUiState.Success -> PhotoDetailsScreen(
                        imgUrl = selectedPhoto.imgUrl,
                        title = (flickrViewModel.photoInfoUiState as PhotoInfoUiState.Success).photo.title._content,
                        username = (flickrViewModel.photoInfoUiState as PhotoInfoUiState.Success).photo.owner.username,
                        description = (flickrViewModel.photoInfoUiState as PhotoInfoUiState.Success).photo.description._content,
                        dateUploaded = formatUnixTimestamp((flickrViewModel.photoInfoUiState as PhotoInfoUiState.Success).photo.dateuploaded),
                        views = (flickrViewModel.photoInfoUiState as PhotoInfoUiState.Success).photo.views
                    ) { flickrViewModel.clearSelectedPhoto() }

                    is PhotoInfoUiState.Error -> PhotoDetailsScreenError(
                        imgUrl = selectedPhoto.imgUrl,
                        title = selectedPhoto.title
                    ){ flickrViewModel.clearSelectedPhoto() }

                }

            } else {
                /** Here the whole model is passed to the Search Screen */
                SearchScreen(
                    viewModel = flickrViewModel,
                    modifier = Modifier
                )
            }
        }
    }
}
