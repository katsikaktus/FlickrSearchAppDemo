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
                SearchScreen(
                    viewModel = flickrViewModel,
                    modifier = Modifier
                )
            }
        }
    }
}
