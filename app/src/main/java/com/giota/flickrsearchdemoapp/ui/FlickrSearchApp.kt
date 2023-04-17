package com.giota.flickrsearchdemoapp.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giota.flickrsearchdemoapp.network.Photo
import com.giota.flickrsearchdemoapp.ui.screens.*


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
            val flickrViewModel: FlickrSearchViewModel =
                viewModel(factory = FlickrSearchViewModel.Factory)


            val selectedPhoto = flickrViewModel.selectedPhoto


            if (selectedPhoto != null) {

                when(flickrViewModel.photoInfoUiState){
                    is PhotoInfoUiState.NoRequest -> {
                        flickrViewModel.getPhotoInfo(
                            photoId = selectedPhoto.id,
                            photoSecret = selectedPhoto.secret
                        )
                    }
                    is PhotoInfoUiState.Success -> PhotoDetailsScreen(
                        (flickrViewModel.photoInfoUiState as PhotoInfoUiState.Success).photo
                    ) { flickrViewModel.clearSelectedPhoto() }

                }


            } else {
                SearchScreen(
                    viewModel = flickrViewModel,
                    modifier = Modifier
                )
            }
        }
    }
}
